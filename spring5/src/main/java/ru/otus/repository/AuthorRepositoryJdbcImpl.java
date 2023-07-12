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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbcImpl implements AuthorRepositoryJdbc {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

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

        return getAuthorById((long) keyHolder.getKey());
    }

    @Override
    public Author getAuthorById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcOperations.queryForObject(
                "select id, first_name, last_name, age, year_birthdate from author where id = :id",
                parameters,
                new AuthorMapper()
        );
    }

    @Override
    public List<Author> findAllAuthorsByIds(List<Long> ids) {
        List<Author> authors = getAllAuthors();

        return authors.stream().filter(a -> ids.contains(a.getId())).toList();
    }

    @Override
    public List<Author> getAllAuthors() {
        return namedParameterJdbcOperations.query(
                "select id, first_name, last_name, age, year_birthdate from author",
                new AuthorMapper()
        );
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        namedParameterJdbcOperations.update(
                "delete from author where id = :id",
                parameters
        );
    }

    @Override
    public List<Author> findAllUsed() {
        return namedParameterJdbcOperations.query(
                "select a.id, a.first_name, a.last_name, a.age, a.year_birthdate " +
                        "from author a join book_author ba on ba.author_id = a.id " +
                        "group by a.id, a.first_name, a.last_name " +
                        "order by a.last_name, a.first_name", new AuthorMapper()
        );
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
