package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbcImpl implements GenreDaoJdbc {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        Integer count = namedParameterJdbcOperations.getJdbcOperations().queryForObject("select count(*) from genre", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public Genre insert(Genre genre) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("genre", genre.getGenre());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "insert into genre (genre) values (:genre)",
                parameters,
                keyHolder);

        return getById((long) keyHolder.getKey());
    }

    @Override
    public Genre update(Genre genre) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("genre", genre.getGenre());

        namedParameterJdbcOperations.update(
                "update genre set genre = :genre where id = :id",
                parameters
        );

        return getById(genre.getId());
    }

    @Override
    public Genre getById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcOperations.queryForObject(
                "select id, genre from genre where id = :id", parameters, new GenreMapper()
        );
    }

    @Override
    public List<Genre> findAllById(Iterable<Long> ids) {
        List<Genre> genres = new ArrayList<>();

        ids.forEach(id -> genres.add(getById(id)));

        return genres;
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select id, genre from genre", new GenreMapper());
    }

    @Override
    public List<Genre> findGenreByBookId(long bookId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("book_id", bookId);

        List<Long> genreIds = namedParameterJdbcOperations.queryForList(
                "select genre_id from book_genre where book_id = :book_id", parameters, Long.class
        );

        return findAllById(genreIds);
    }

    @Override
    public void deleteGenreByBookId(long bookId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("book_id", bookId);

        namedParameterJdbcOperations.update(
                "delete from book_genre where book_id = :book_id",
                parameters
        );
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedParameterJdbcOperations.update(
                "delete from genre where id = :id", parameters
        );
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String genre = rs.getString("genre");

            return new Genre(id, genre);
        }
    }
}
