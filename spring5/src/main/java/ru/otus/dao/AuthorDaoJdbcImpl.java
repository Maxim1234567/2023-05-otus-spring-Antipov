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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbcImpl implements AuthorDaoJdbc {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        return 0;
    }

    @Override
    public Author insert(Author author) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("first_name", author.getFirstName())
                .addValue("last_name", author.getLastName())
                .addValue("age", author.getAge())
                .addValue("year_birthdate", author.getYearBirthdate());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "insert into author (first_name, last_name, age, year_birthdate) values (:first_name, :last_name, :age, :year_birthdate)",
                parameters,
                keyHolder
        );

        return getById((long) keyHolder.getKey());
    }

    @Override
    public Author update(Author value) {
        return null;
    }

    @Override
    public Author getById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcOperations.queryForObject(
                "select id, first_name, last_name, age, year_birthdate from author where id = :id",
                parameters,
                new AuthorMapper()
        );
    }

    @Override
    public List<Author> findAllById(Iterable<Long> ids) {
        List<Author> authors = new ArrayList<>();

        ids.forEach(id -> authors.add(getById(id)));

        return authors;
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query(
                "select id, first_name, last_name, age, year_birthdate from author",
                new AuthorMapper()
        );
    }

    @Override
    public List<Author> findAuthorByBookId(long bookId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("book_id", bookId);

        List<Long> authorIds = namedParameterJdbcOperations.queryForList(
                "select author_id from book_author where book_id = :book_id",
                parameters,
                Long.class
        );

        return findAllById(authorIds);
    }

    @Override
    public void deleteAuthorByBookId(long bookId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("book_id", bookId);

        namedParameterJdbcOperations.update(
                "delete from book_author where book_id = :book_id",
                parameters
        );
    }

    @Override
    public void deleteById(long id) {

    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            int age = rs.getInt("age");
            int yearBirthdate = rs.getInt("year_birthdate");

            return new Author(
                    id, firstName, lastName, age, yearBirthdate
            );
        }
    }
}
