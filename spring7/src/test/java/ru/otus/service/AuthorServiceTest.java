package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.Utils;
import ru.otus.domain.Author;
import ru.otus.dto.AuthorDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Service to work with author should")
@SpringBootTest
@ActiveProfiles("author")
public class AuthorServiceTest {
    private static final AuthorDto EXISTING_AUTHOR = new AuthorDto(
            400L, "Irvine", "Welsh", 64, 1958
    );

    private static final List<AuthorDto> EXPECTED_AUTHORS = List.of(
            new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951),
            new AuthorDto(200L, "Ivan", "Efremov", 64, 1908),
            new AuthorDto(300L, "Isaac", "Asimov", 72, 1919),
            new AuthorDto(400L, "Irvine", "Welsh", 64, 1958),
            new AuthorDto(500L, "Lyubov", "Voronkova", 70, 1906)
    );

    private static final AuthorDto NOT_EXISTS_AUTHOR = new AuthorDto(
            null, "Lyubov", "Voronkova", 70, 1906
    );

    @Autowired
    private AuthorService authorService;

    @DisplayName("should correct save author")
    @Test
    public void shouldCorrectSaveGenre() {
        AuthorDto author = authorService.save(NOT_EXISTS_AUTHOR);
        List<AuthorDto> authors = authorService.getAll();
        authorService.delete(author);

        assertTrue(authors.contains(author));
    }

    @DisplayName("should correct return all authors")
    @Test
    public void shouldCorrectReturnAllGenres() {
        List<AuthorDto> authors = authorService.getAll();

        Utils.assertEqualsAuthorListDto(EXPECTED_AUTHORS, authors);
    }

    @DisplayName("should correct delete author")
    @Test
    public void shouldCorrectDeleteGenre() {
        AuthorDto author = authorService.save(NOT_EXISTS_AUTHOR);
        List<AuthorDto> authorsWithNotExistsGenre = authorService.getAll();

        authorService.delete(author);
        List<AuthorDto> authorsWithoutNotExistsGenre = authorService.getAll();

        assertTrue(authorsWithNotExistsGenre.contains(author));
        Utils.assertEqualsAuthorListDto(EXPECTED_AUTHORS, authorsWithoutNotExistsGenre);
    }

    @DisplayName("should correct return author")
    @Test
    public void shouldCorrectReturnAuthor() {
        AuthorDto expected = EXPECTED_AUTHORS.get(0);
        AuthorDto result = authorService.getAuthorById(expected.getId());

        assertEquals(expected, result);
    }

    @Test
    public void shouldCorrectReturnEmptyAuthorDtoIfAuthorNotExists() {
        AuthorDto expected = new AuthorDto();
        AuthorDto result = authorService.getAuthorById(111L);

        assertEquals(expected, result);
    }
}