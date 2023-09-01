package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.convert.AuthorConvertAuthorDto;
import ru.otus.convert.AuthorDtoConvertAuthor;
import ru.otus.domain.Author;
import ru.otus.dto.AuthorDto;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.service.impl.AuthorServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.otus.Utils.assertEqualsAuthor;
import static ru.otus.Utils.assertEqualsAuthorDto;
import static ru.otus.Utils.assertEqualsAuthorListDto;

@DisplayName("Service to work with author should")
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    private static final List<AuthorDto> EXPECTED_AUTHORS_DTO = List.of(
            new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951),
            new AuthorDto(200L, "Ivan", "Efremov", 64, 1908),
            new AuthorDto(300L, "Isaac", "Asimov", 72, 1919)
    );

    private static final List<Author> EXPECTED_AUTHORS = List.of(
            new Author(100L, "Herbert", "Shieldt", 72, 1951),
            new Author(200L, "Ivan", "Efremov", 64, 1908),
            new Author(300L, "Isaac", "Asimov", 72, 1919)
    );

    AuthorDto authorDto = new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951);

    Author author = new Author(100L, "Herbert", "Shieldt", 72, 1951);

    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorDtoConvertAuthor convertAuthor;

    @Mock
    private AuthorConvertAuthorDto convertAuthorDto;

    @BeforeEach
    public void setUp() {
        authorService = new AuthorServiceImpl(
                authorRepository,
                convertAuthor,
                convertAuthorDto
        );
    }

    @DisplayName("should correct save author")
    @Test
    public void shouldCorrectSaveAuthor() {
        given(convertAuthor.convert(eq(authorDto)))
                .willReturn(author);
        given(authorRepository.save(eq(author)))
                .willReturn(author);
        given(convertAuthorDto.convert(eq(author)))
                .willReturn(authorDto);

        AuthorDto result = authorService.create(authorDto);

        assertEqualsAuthorDto(result, authorDto);

        verify(convertAuthor, times(1))
                .convert(eq(authorDto));
        verify(authorRepository, times(1))
                .save(eq(author));
        verify(convertAuthorDto, times(1))
                .convert(eq(author));
    }

    @DisplayName("should correct return all authors")
    @Test
    public void shouldCorrectReturnAllGenres() {
        given(convertAuthorDto.convert(eq(EXPECTED_AUTHORS.get(0))))
                .willReturn(EXPECTED_AUTHORS_DTO.get(0));
        given(convertAuthorDto.convert(eq(EXPECTED_AUTHORS.get(1))))
                .willReturn(EXPECTED_AUTHORS_DTO.get(1));
        given(convertAuthorDto.convert(eq(EXPECTED_AUTHORS.get(2))))
                .willReturn(EXPECTED_AUTHORS_DTO.get(2));
        given(authorRepository.findAll())
                .willReturn(EXPECTED_AUTHORS);

        List<AuthorDto> results = authorService.getAll();

        assertEqualsAuthorListDto(results, EXPECTED_AUTHORS_DTO);

        verify(convertAuthorDto, times(1))
                .convert(eq(EXPECTED_AUTHORS.get(0)));
        verify(convertAuthorDto, times(1))
                .convert(eq(EXPECTED_AUTHORS.get(1)));
        verify(convertAuthorDto, times(1))
                .convert(eq(EXPECTED_AUTHORS.get(2)));
    }

    @DisplayName("should correct delete author")
    @Test
    public void shouldCorrectDeleteAuthor() {
        authorService.delete(authorDto);

        verify(authorRepository, times(1))
                .deleteById(eq(author.getId()));
    }

    @DisplayName("should correct return author")
    @Test
    public void shouldCorrectReturnAuthor() {
        given(authorRepository.findById(eq(author.getId())))
                .willReturn(Optional.of(author));
        given(convertAuthorDto.convert(eq(author)))
                .willReturn(authorDto);

        AuthorDto result = authorService.getAuthorById(authorDto.getId());

        assertEqualsAuthorDto(result, authorDto);

        verify(authorRepository, times(1))
                .findById(author.getId());
        verify(convertAuthorDto, times(1))
                .convert(eq(author));
    }

    @Test
    @DisplayName("should throws NotFoundException if author not exists")
    public void shouldThrowsNotFoundExceptionIfAuthorNotExists() {
        given(authorRepository.findById(author.getId()))
                .willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.getAuthorById(author.getId()));

        verify(authorRepository, times(1))
                .findById(author.getId());
        verify(convertAuthorDto, times(0))
                .convert(any(Author.class));
    }
}