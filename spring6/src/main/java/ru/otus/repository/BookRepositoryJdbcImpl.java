package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.ext.BookAuthorRelation;
import ru.otus.repository.ext.BookGenreRelation;
import ru.otus.repository.ext.BookResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbcImpl implements BookRepositoryJdbc {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final AuthorRepositoryJdbc authorRepository;

    private final GenreRepositoryJdbc genreRepository;

    @Override
    public Book insert(Book book) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("year_issue", book.getYearIssue())
                .addValue("number_pages", book.getNumberPages());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "insert into book(name, year_issue, number_pages) values (:name, :year_issue, :number_pages)",
                parameters,
                keyHolder
        );

        long bookId = (long) keyHolder.getKey();

        saveRelationBookAndGenres(bookId, book.getGenres().stream().map(Genre::getId).toList());
        saveRelationBookAndAuthors(bookId, book.getAuthors().stream().map(Author::getId).toList());

        return findById(bookId);
    }

    @Override
    public Book update(Book book) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("name", book.getName())
                .addValue("year_issue", book.getYearIssue())
                .addValue("number_pages", book.getNumberPages());

        namedParameterJdbcOperations.update(
                "update book set name = :name, year_issue = :year_issue, number_pages = :number_pages where id = :id",
                parameters
        );

        deleteRelationGenresByBookId(book.getId());
        deleteRelationAuthorsByBookId(book.getId());

        saveRelationBookAndGenres(book.getId(), book.getGenres().stream().map(Genre::getId).toList());
        saveRelationBookAndAuthors(book.getId(), book.getAuthors().stream().map(Author::getId).toList());

        return findById(book.getId());
    }

    @Override
    public Book findById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        Book book = namedParameterJdbcOperations.queryForObject(
                "select b.id, b.name, b.year_issue, b.number_pages from book b where b.id = :id",
                parameters,
                new BookMapper()
        );

        Map<Long, Book> map = Map.of(book.getId(), book);
        mergeAuthor(map, authorRepository.findByBookId(id), getAllRelationsAuthor());
        mergeGenre(map, genreRepository.findByBookId(id), getAllRelationsGenre());

        return book;
    }

    @Override
    public List<Book> findByIds(List<Long> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ids", ids);

        Map<Long, Book> books = namedParameterJdbcOperations.query(
                "select b.id, b.name, b.year_issue, b.number_pages from book b where b.id in (:ids)",
                parameters,
                new BookResultSetExtractor()
        );

        mergeAuthor(books, authorRepository.findByBookIds(ids), getAllRelationsAuthor());
        mergeGenre(books, genreRepository.findByBookIds(ids), getAllRelationsGenre());

        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    @Override
    public List<Book> getAllBooks() {
        Map<Long, Book> books = namedParameterJdbcOperations.query(
                "select b.id, b.name, b.year_issue, b.number_pages from book b",
                new BookResultSetExtractor()
        );

        mergeAuthor(books, authorRepository.findAll(), getAllRelationsAuthor());
        mergeGenre(books, genreRepository.findAll(), getAllRelationsGenre());

        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        deleteRelationGenresByBookId(id);
        deleteRelationAuthorsByBookId(id);

        namedParameterJdbcOperations.update(
                "delete from book where id = :id",
                parameters
        );
    }

    private void saveRelationBookAndGenres(long bookId, List<Long> genreIds) {
        SqlParameterSource[] parameters = new SqlParameterSource[genreIds.size()];

        for(int i = 0; i < genreIds.size(); i++) {
            SqlParameterSource parameter = new MapSqlParameterSource()
                    .addValue("book_id", bookId)
                    .addValue("genre_id", genreIds.get(i));

            parameters[i] = parameter;
        }

        namedParameterJdbcOperations.batchUpdate(
                "insert into book_genre (book_id, genre_id) values (:book_id, :genre_id)",
                parameters
        );
    }

    private void deleteRelationGenresByBookId(long bookId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("book_id", bookId);

        namedParameterJdbcOperations.update(
                "delete from book_genre where book_id = :book_id",
                parameters
        );
    }

    private void saveRelationBookAndAuthors(long bookId, List<Long> authorIds) {
        SqlParameterSource[] parameters = new SqlParameterSource[authorIds.size()];

        for(int i = 0; i < authorIds.size(); i++) {
            SqlParameterSource parameter = new MapSqlParameterSource()
                    .addValue("book_id", bookId)
                    .addValue("author_id", authorIds.get(i));

            parameters[i] = parameter;
        }

        namedParameterJdbcOperations.batchUpdate(
                "insert into book_author (book_id, author_id) values (:book_id, :author_id)",
                parameters
        );
    }

    private void deleteRelationAuthorsByBookId(long bookId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("book_id", bookId);

        namedParameterJdbcOperations.update(
                "delete from book_author where book_id = :book_id",
                parameters
        );
    }

    private List<BookAuthorRelation> getAllRelationsAuthor() {
        return namedParameterJdbcOperations.query(
                "select book_id, author_id from book_author ba order by author_id, book_id",
                (rs, i) -> new BookAuthorRelation(rs.getLong("book_id"), rs.getLong("author_id"))
        );
    }

    private List<BookGenreRelation> getAllRelationsGenre() {
        return namedParameterJdbcOperations.query(
                "select book_id, genre_id from book_genre bg order by genre_id, book_id",
                (rs, i) -> new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id"))
        );
    }

    private void mergeAuthor(Map<Long, Book> books, List<Author> authors, List<BookAuthorRelation> relations) {
        Map<Long, Author> authorsMap = authors.stream().collect(Collectors.toMap(Author::getId, Function.identity()));
        relations.forEach(r -> {
            if(books.containsKey(r.getBookId()) && authorsMap.containsKey(r.getAuthorId())) {
                books.get(r.getBookId()).getAuthors().add(authorsMap.get(r.getAuthorId()));
            }
        });
    }

    private void mergeGenre(Map<Long, Book> books, List<Genre> genres, List<BookGenreRelation> relations) {
        Map<Long, Genre> genreMap = genres.stream().collect(Collectors.toMap(Genre::getId, Function.identity()));
        relations.forEach(r -> {
            if(books.containsKey(r.getBookId()) && genreMap.containsKey(r.getGenreId())) {
                books.get(r.getBookId()).getGenres().add(genreMap.get(r.getGenreId()));
            }
        });
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int yearIssue = rs.getInt("year_issue");
            int numberPages = rs.getInt("number_pages");

            return new Book(
                    id,
                    name,
                    yearIssue,
                    numberPages,
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }
    }
}