package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.Utils;
import ru.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao to work with authors should")
@DataJpaTest
@Import(AuthorRepositoryJpaImpl.class)
public class AuthorRepositoryJpaTest {
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
    private AuthorRepositoryJpaImpl authorRepository;

    @DisplayName("correctly save the genre without a given ID in the database")
    @Test
    public void shouldCorrectSaveAuthorWithoutId() {
        Author author = authorRepository.save(NOT_EXISTS_AUTHOR);
        Author result = authorRepository.findById(author.getId()).get();

        assertEquals(author, result);
    }

    @DisplayName("correctly return author by id")
    @Test
    public void shouldCorrectReturnAuthorById() {
        Author result = authorRepository.findById(EXISTING_AUTHOR.getId()).get();
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

        List<Author> result = authorRepository.findByIds(authorIds);
        Utils.assertEqualsAuthorList(excepted, result);
    }

    @DisplayName("correctly returns the expected list of authors")
    @Test
    public void shouldCorrectReturnExceptedAuthorList() {
        List<Author> result = authorRepository.findAll();
        Utils.assertEqualsAuthorList(EXPECTED_AUTHORS, result);
    }

    @DisplayName("correctly return the authors by book id")
    @Test
    public void shouldCorrectReturnAuthorsByBookId() {
        long bookId = 100L;
        List<Author> result = authorRepository.findByBookId(bookId);
        Utils.assertEqualsAuthorList(List.of(EXPECTED_AUTHORS.get(0)), result);
    }

    @DisplayName("correctly return the authors by book ids")
    @Test
    public void shouldCorrectReturnAuthorsByBookIds() {
        List<Long> bookIds = List.of(100L, 200L, 300L);
        List<Author> result = authorRepository.findByBookIds(bookIds);
        Utils.assertEqualsAuthorList(List.of(EXPECTED_AUTHORS.get(0), EXPECTED_AUTHORS.get(1), EXPECTED_AUTHORS.get(2)), result);
    }

    @DisplayName("correctly delete a genre by its id")
    @Test
    public void shouldCorrectDeleteAuthorById() {
        assertThat(authorRepository.findById(EXISTING_AUTHOR.getId()))
                .isNotNull();

        authorRepository.deleteById(EXISTING_AUTHOR.getId());

        assertThat(authorRepository.findById(EXISTING_AUTHOR.getId()))
                .isNull();
    }

    @Test
    public void shouldCorrectReturnEmptyOptionalIfAuthorNotExists() {
        assertTrue(false);
    }
}