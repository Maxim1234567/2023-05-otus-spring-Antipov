package ru.otus.dao;

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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbcImpl implements BookDaoJdbc {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        return 0;
    }

    @Override
    public Book insert(Book value) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", value.getName())
                .addValue("year_issue", value.getYearIssue())
                .addValue("number_pages", value.getNumberPages());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "insert into book(name, year_issue, number_pages) values (:name, :year_issue, :number_pages)",
                parameters,
                keyHolder
        );

        return getById((long) keyHolder.getKey());
    }

    @Override
    public Book update(Book value) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", value.getId())
                .addValue("name", value.getName())
                .addValue("year_issue", value.getYearIssue())
                .addValue("number_pages", value.getNumberPages());

        namedParameterJdbcOperations.update(
                "update book set name = :name, year_issue = :year_issue, number_pages = :number_pages where id = :id",
                parameters
        );

        return getById(value.getId());
    }

    @Override
    public Book getById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcOperations.queryForObject(
                "select id, name, year_issue, number_pages from book where id = :id", parameters, new BookMapper()
        );
    }

    @Override
    public List<Book> findAllById(Iterable<Long> ids) {
        List<Book> books = new ArrayList<>();

        ids.forEach(id -> books.add(getById(id)));

        return books;
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(
                "select id, name, year_issue, number_pages from book", new BookMapper()
        );
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("book_id", id);

        namedParameterJdbcOperations.update(
                "delete from book_genre where book_id = :book_id",
                parameters
        );

        namedParameterJdbcOperations.update(
                "delete from book_author where book_id = :book_id",
                parameters
        );

        namedParameterJdbcOperations.update(
                "delete from book where id = :book_id",
                parameters
        );
    }

    @Override
    public List<Book> findBookByGenreId(long genreId) {
        SqlParameterSource genreParameters = new MapSqlParameterSource()
                .addValue("genre_id", genreId);

        List<Long> bookIds = namedParameterJdbcOperations.queryForList(
                "select book_id from book_genre where genre_id = :genre_id",
                genreParameters,
                Long.class);

        return findAllById(bookIds);
    }

    @Override
    public void saveBookAndGenre(long bookId, List<Long> genreIds) {
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

    @Override
    public void saveBookAndAuthors(long bookId, List<Long> authorIds) {
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

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int yearIssue = rs.getInt("year_issue");
            int numberPages = rs.getInt("number_pages");

            return new Book(id, name, yearIssue, numberPages);
        }
    }
}
