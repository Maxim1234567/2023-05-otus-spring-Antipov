package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.impl.DomainConvertImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Component to convert domain to string should")
public class DomainConvertTest {
    private final String SEPARATOR = "----------------------------------------";

    private final String exceptedGenreToString = "100. | Fiction";
    private final String exceptedAuthorToString = "100. | Herbert Shieldt | 1951 | 72";
    private final String exceptedBookToString = new StringBuilder()
            .append("100. | Java. Complete guide | 2022 | 1344\n")
            .append(SEPARATOR + "\n")
            .append("  100. | Herbert Shieldt | 1951 | 72\n")
            .append(SEPARATOR + "\n")
            .append("  100. | Fiction")
            .append("\n")
            .toString();

    private final GenreDto genre = new GenreDto(100L, "Fiction");
    private final AuthorDto author = new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951);
    private final BookDto book = new BookDto(
            100L, "Java. Complete guide", 2022, 1344,
            List.of(genre),
            List.of(author)
    );

    private DomainConvert domainConvert;

    @BeforeEach
    public void setUp() {
        domainConvert = new DomainConvertImpl();
    }

    @DisplayName("correctly convert genre to string")
    @Test
    public void shouldCorrectConvertGenreToString() {
        String result = domainConvert.convertGenreToString(genre);
        assertEquals(exceptedGenreToString, result);
    }

    @DisplayName("correctly convert author to string")
    @Test
    public void shouldCorrectConvertAuthorToString() {
        String result = domainConvert.convertAuthorToString(author);
        assertEquals(exceptedAuthorToString, result);
    }

    @DisplayName("correctly convert book to string")
    @Test
    public void shouldCorrectConvertBookToString() {
        String result = domainConvert.convertBookToString(book);
        assertEquals(exceptedBookToString, result);
    }
}