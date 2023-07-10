package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DisplayName("Dao to work with authors should")
@JdbcTest
@Import(AuthorDaoJdbcImpl.class)
public class AuthorDaoJdbcTest {
    private static final int CORRECT_GENRE_COUNT = 4;

    private static final Author EXISTING_AUTHOR = new Author(
            400L, "Irvine", "Welsh", 64, 1958
    );

    private static final List<Author> EXPECTED_AUTHORS = List.of(
            new Author(100L, "Herbert", "Shieldt", 72, 1951),
            new Author(200L, "Ivan", "Efremov", 64, 1908),
            new Author(300L, "Isaac", "Asimov", 72, 1919),
            new Author(400L, "Irvine", "Welsh", 64, 1958)
    );

    private static final Author NOT_EXISTS_AUTHOR = new Author(
            null, "Lyubov", "Voronkova", 70, 1906
    );

    @Autowired
    private AuthorDaoJdbcImpl authorDao;

    @DisplayName("return correctly the number of genres in the database")
    @Test
    public void shouldReturnCorrectAuthorCount() {
    }

    @DisplayName("correctly save the genre without a given ID in the database")
    @Test
    public void shouldCorrectSaveAuthorWithoutId() {
        Author author = authorDao.insert(NOT_EXISTS_AUTHOR);
        Author result = authorDao.getById(author.getId());

        assertEquals(author, result);
    }

    @DisplayName("correctly updates the genre with the given ID in the database")
    @Test
    public void shouldCorrectUpdateAuthorWithId() {
    }

    @DisplayName("correctly return author by id")
    @Test
    public void shouldCorrectReturnAuthorById() {
        Author result = authorDao.getById(EXISTING_AUTHOR.getId());
        assertEquals(EXISTING_AUTHOR, result);
    }

    @DisplayName("correctly return authors by ids")
    @Test
    public void shouldCorrectReturnAuthorsByIds() {
        List<Long> authorIds = List.of(
                EXPECTED_AUTHORS.get(0).getId(), EXPECTED_AUTHORS.get(1).getId(), EXPECTED_AUTHORS.get(2).getId(),
                EXPECTED_AUTHORS.get(3).getId()
        );

        List<Author> result = authorDao.findAllById(authorIds);
        assertEqualsList(EXPECTED_AUTHORS, result);
    }

    @DisplayName("correctly returns the expected list of authors")
    @Test
    public void shouldCorrectReturnExceptedAuthorList() {
        List<Author> result = authorDao.getAll();
        assertEqualsList(EXPECTED_AUTHORS, result);
    }

    @DisplayName("correctly delete a genre by its id")
    @Test
    public void shouldCorrectDeleteAuthorById() {
    }

    public void assertEqualsList(List<Author> genres1, List<Author> genres2) {
        assertIterableEquals(
                genres1.stream().sorted(Comparator.comparing(Author::getId)).toList(),
                genres2.stream().sorted(Comparator.comparing(Author::getId)).toList()
        );
    }
}
