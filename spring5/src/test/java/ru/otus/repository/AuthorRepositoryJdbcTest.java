package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.Utils;
import ru.otus.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao to work with authors should")
@JdbcTest
@Import(AuthorRepositoryJdbcImpl.class)
public class AuthorRepositoryJdbcTest {
    private static final int CORRECT_GENRE_COUNT = 4;

    private static final Author EXISTING_AUTHOR = new Author(
            400L, "Irvine", "Welsh", 64, 1958
    );

    private static final List<Author> EXPECTED_AUTHORS = List.of(
            new Author(100L, "Herbert", "Shieldt", 72, 1951),
            new Author(200L, "Ivan", "Efremov", 64, 1908),
            new Author(300L, "Isaac", "Asimov", 72, 1919),
            new Author(400L, "Irvine", "Welsh", 64, 1958),
            new Author(500L, "Lyubov", "Voronkova", 70, 1906)
    );

    private static final Author NOT_EXISTS_AUTHOR = new Author(
            null, "Lyubov", "Voronkova", 70, 1906
    );

    @Autowired
    private AuthorRepositoryJdbcImpl authorDao;

    @DisplayName("correctly save the genre without a given ID in the database")
    @Test
    public void shouldCorrectSaveAuthorWithoutId() {
        Author author = authorDao.insert(NOT_EXISTS_AUTHOR);
        Author result = authorDao.getAuthorById(author.getId());

        assertEquals(author, result);
    }

    @DisplayName("correctly return author by id")
    @Test
    public void shouldCorrectReturnAuthorById() {
        Author result = authorDao.getAuthorById(EXISTING_AUTHOR.getId());
        assertEquals(EXISTING_AUTHOR, result);
    }

    @DisplayName("correctly return authors by ids")
    @Test
    public void shouldCorrectReturnAuthorsByIds() {
        List<Long> authorIds = List.of(
                EXPECTED_AUTHORS.get(0).getId(),
                EXPECTED_AUTHORS.get(1).getId(),
                EXPECTED_AUTHORS.get(2).getId(),
                EXPECTED_AUTHORS.get(3).getId()
        );

        List<Author> excepted = List.of(
                EXPECTED_AUTHORS.get(0),
                EXPECTED_AUTHORS.get(1),
                EXPECTED_AUTHORS.get(2),
                EXPECTED_AUTHORS.get(3)
        );

        List<Author> result = authorDao.findAllAuthorsByIds(authorIds);
        Utils.assertEqualsAuthorList(excepted, result);
    }

    @DisplayName("correctly returns the expected list of authors")
    @Test
    public void shouldCorrectReturnExceptedAuthorList() {
        List<Author> result = authorDao.getAllAuthors();
        Utils.assertEqualsAuthorList(EXPECTED_AUTHORS, result);
    }

    @DisplayName("correctly delete a genre by its id")
    @Test
    public void shouldCorrectDeleteAuthorById() {
        assertDoesNotThrow(() -> authorDao.getAuthorById(EXISTING_AUTHOR.getId()));

        authorDao.deleteById(EXISTING_AUTHOR.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getAuthorById(EXISTING_AUTHOR.getId()));
    }

    @DisplayName("correctly return author which used")
    @Test
    public void shouldCorrectReturnAuthorWhichUsed() {
        List<Author> result = authorDao.findAllUsed();

        List<Author> excepted = List.of(
                EXPECTED_AUTHORS.get(0),
                EXPECTED_AUTHORS.get(1),
                EXPECTED_AUTHORS.get(2)
        );

        Utils.assertEqualsAuthorList(excepted, result);
    }
}
