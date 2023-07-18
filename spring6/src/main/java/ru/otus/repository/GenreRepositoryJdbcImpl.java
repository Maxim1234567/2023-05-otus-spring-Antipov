package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJdbcImpl implements GenreRepositoryJdbc {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Genre insert(Genre genre) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("genre", genre.getGenre());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "insert into genre (genre) values (:genre)",
                parameters,
                keyHolder);

        return findById((long) keyHolder.getKey());
    }

    @Override
    public Genre findById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcOperations.queryForObject(
                "select id, genre from genre where id = :id", parameters, new GenreMapper()
        );
    }

    @Override
    public List<Genre> findByIds(List<Long> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ids", ids);

        return namedParameterJdbcOperations.query(
                "select id, genre from genre where id in (:ids)",
                parameters,
                new GenreMapper()
        );
    }

    @Override
    public List<Genre> findByBookId(long bookId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("book_id", bookId);

        return namedParameterJdbcOperations.query(
                "select id, genre from genre g " +
                        "where g.id in (select genre_id from book_genre bg where bg.book_id = :book_id)",
                parameters,
                new GenreMapper()
        );
    }

    @Override
    public List<Genre> findByBookIds(List<Long> bookIds) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("book_ids", bookIds);

        return namedParameterJdbcOperations.query(
                "select id, genre from genre g " +
                        "where g.id in (select genre_id from book_genre bg where bg.book_id in (:book_ids))",
                parameters,
                new GenreMapper()
        );
    }

    @Override
    public List<Genre> findAll() {
        return namedParameterJdbcOperations.query("select id, genre from genre", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedParameterJdbcOperations.update(
                "delete from genre where id = :id", parameters
        );
    }

    @Override
    public List<Genre> findAllUsed() {
        return namedParameterJdbcOperations.query(
                "select distinct g.id, g.genre from genre g join book_genre bg on bg.genre_id = g.id " +
                        "order by g.genre", new GenreMapper()
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