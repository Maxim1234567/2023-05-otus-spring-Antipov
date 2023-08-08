package ru.otus.service;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.AuthorDto;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.service.impl.IOServiceStreams;

import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.Utils.assertEqualsAuthorDto;
import static ru.otus.Utils.assertEqualsAuthorListDto;

@DisplayName("Service to work with author should")
@SpringBootTest
public class AuthorServiceTest {
    private static final AuthorDto EXISTING_AUTHOR = new AuthorDto(
            "400", "Irvine", "Welsh", 64, 1958
    );

    private static final List<AuthorDto> EXPECTED_AUTHORS = List.of(
            new AuthorDto("100", "Herbert", "Shieldt", 72, 1951),
            new AuthorDto("200", "Ivan", "Efremov", 64, 1908),
            new AuthorDto("300", "Isaac", "Asimov", 72, 1919),
            new AuthorDto("400", "Irvine", "Welsh", 64, 1958),
            new AuthorDto("500", "Lyubov", "Voronkova", 70, 1906)
    );

    private static final AuthorDto NOT_EXISTS_AUTHOR = new AuthorDto(
            null, "Lyubov", "Voronkova", 70, 1906
    );

    @Autowired
    private AuthorService authorService;

    @DisplayName("should correct save author")
    @Test
    public void shouldCorrectSaveAuthor() {
        AuthorDto expected = authorService.save(NOT_EXISTS_AUTHOR);
        AuthorDto result = authorService.getAuthorById(expected.getId());
        authorService.delete(expected);

        assertEqualsAuthorDto(expected, result);
    }

    @DisplayName("should correct return all authors")
    @Test
    public void shouldCorrectReturnAllGenres() {
        List<AuthorDto> authors = authorService.getAll();

        assertEqualsAuthorListDto(EXPECTED_AUTHORS, authors);
    }

    @DisplayName("should correct delete author")
    @Test
    public void shouldCorrectDeleteAuthor() {
        AuthorDto expected = authorService.save(NOT_EXISTS_AUTHOR);
        assertDoesNotThrow(() -> authorService.getAuthorById(expected.getId()));
        assertDoesNotThrow(() -> authorService.delete(expected));
        assertThrows(NotFoundException.class, () -> authorService.getAuthorById(expected.getId()));
    }

    @DisplayName("should correct return author")
    @Test
    public void shouldCorrectReturnAuthor() {
        AuthorDto expected = EXPECTED_AUTHORS.get(0);
        AuthorDto result = authorService.getAuthorById(expected.getId());

        assertEqualsAuthorDto(expected, result);
    }

    @Test
    @DisplayName("should throws NotFoundException if author not exists")
    public void shouldThrowsNotFoundExceptionIfAuthorNotExists() {
        assertThrows(NotFoundException.class, () -> authorService.getAuthorById("111"));
    }
}