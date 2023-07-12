package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.Utils;
import ru.otus.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Service to work with author should")
@SpringBootTest
@ActiveProfiles("author")
public class AuthorServiceTest {
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
    private AuthorService authorService;

    @DisplayName("should correct save author")
    @Test
    public void shouldCorrectSaveGenre() {
        Author author = authorService.save(NOT_EXISTS_AUTHOR);
        List<Author> authors = authorService.getAll();
        authorService.delete(author);

        assertTrue(authors.contains(author));
    }

    @DisplayName("should correct return all authors")
    @Test
    public void shouldCorrectReturnAllGenres() {
        List<Author> authors = authorService.getAll();

        Utils.assertEqualsAuthorList(EXPECTED_AUTHORS, authors);
    }

    @DisplayName("should correct delete author")
    @Test
    public void shouldCorrectDeleteGenre() {
        Author author = authorService.save(NOT_EXISTS_AUTHOR);
        List<Author> authorsWithNotExistsGenre = authorService.getAll();

        authorService.delete(author);
        List<Author> authorsWithoutNotExistsGenre = authorService.getAll();

        assertTrue(authorsWithNotExistsGenre.contains(author));
        Utils.assertEqualsAuthorList(EXPECTED_AUTHORS, authorsWithoutNotExistsGenre);
    }

    @DisplayName("should correct return author")
    @Test
    public void shouldCorrectReturnAuthor() {
        Author expected = EXPECTED_AUTHORS.get(0);
        Author result = authorService.getAuthorById(expected.getId());

        assertEquals(expected, result);
    }
}
