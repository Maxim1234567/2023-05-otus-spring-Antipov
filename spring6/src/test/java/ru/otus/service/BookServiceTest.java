package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.Utils;
import ru.otus.convert.BookConvertBookDto;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.GenreDto;
import ru.otus.exception.AuthorNotFound;
import ru.otus.exception.BookNotFound;
import ru.otus.exception.GenreNotFound;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Service to work with book should")
@SpringBootTest
@ActiveProfiles("book")
public class BookServiceTest {
    private static final BookDto EXISTING_BOOK = new BookDto(
            300L,
            "FOUNDATION",
            2022,
            320,
            List.of(
                    new GenreDto(200L, "Novel"),
                    new GenreDto(600L, "Drama"),
                    new GenreDto(700L, "Popular science literature")
            ),
            List.of(
                    new AuthorDto(300L, "Isaac", "Asimov", 72, 1919)
            )
    );

    private static final List<BookDto> EXPECTED_BOOK = List.of(
            new BookDto(
                    100L,
                    "Java. Complete guide",
                    2022,
                    1344,
                    List.of(
                            new GenreDto(900L, "Reference books and professional literature"),
                            new GenreDto(1000L, "Hobbies, skills")
                    ),
                    List.of(
                            new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951)
                    )
            ),
            new BookDto(
                    200L,
                    "Starships. Andromeda's nebula",
                    1987,
                    400,
                    List.of(
                            new GenreDto(200L, "Novel"),
                            new GenreDto(600L, "Drama"),
                            new GenreDto(700L, "Popular science literature")
                    ),
                    List.of(
                            new AuthorDto(200L, "Ivan", "Efremov", 64, 1908)
                    )
            ),
            new BookDto(
                    300L,
                    "FOUNDATION",
                    2022,
                    320,
                    List.of(
                            new GenreDto(200L, "Novel"),
                            new GenreDto(600L, "Drama"),
                            new GenreDto(700L, "Popular science literature")
                    ),
                    List.of(
                            new AuthorDto(300L, "Isaac", "Asimov", 72, 1919)
                    )
            ),
            new BookDto(
                    400L,
                    "Alice's Adventures in Wonderland",
                    1865,
                    225,
                    List.of(),
                    List.of()
            )
    );

    private static final BookDto NOT_EXISTS_BOOK_WITH_NOT_EXISTS_AUTHOR_AND_GENRE = new BookDto(
            null,
            "Son of Zeus",
            2023,
            1024,
            List.of(
                    new GenreDto(null, "Modern domestic prose")
            ),
            List.of(
                    new AuthorDto(
                            null, "Lyubov", "Voronkova", 70, 1906
                    )
            )
    );

    private static final BookDto NOT_EXISTS_BOOK = new BookDto(
            null,
            "Son of Zeus",
            2023,
            1024,
            List.of(
                    new GenreDto(1100L, "Modern domestic prose")
            ),
            List.of(
                    new AuthorDto(
                            500L, "Lyubov", "Voronkova", 70, 1906
                    )
            )
    );

    @Autowired
    private BookService bookService;

    @Autowired
    private BookConvertBookDto convertBook;

    @DisplayName("correctly return book with genres and authors")
    @Test
    public void shouldCorrectReturnBookWithGenreAndAuthors() {
        BookDto result = bookService.getBookById(EXISTING_BOOK.getId());
        assertEquals(EXISTING_BOOK, result);
    }

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() {
        List<BookDto> result = bookService.getAllBooks();
        Utils.assertEqualsBookListDto(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly save book with not exists authors and genres")
    @Test
    public void shouldCorrectSaveBookWithNotExistsAuthorAndGenre() {
        BookDto book = bookService.save(NOT_EXISTS_BOOK_WITH_NOT_EXISTS_AUTHOR_AND_GENRE);
        BookDto result = bookService.getBookById(book.getId());
        bookService.delete(book);

        assertEqualsBook(result, NOT_EXISTS_BOOK_WITH_NOT_EXISTS_AUTHOR_AND_GENRE);
    }

    @DisplayName("correctly save book with authors and genres")
    @Test
    public void shouldCorrectSaveBookWithAuthorAndGenre() {
        BookDto book = bookService.save(NOT_EXISTS_BOOK);
        BookDto result = bookService.getBookById(book.getId());
        bookService.delete(book);

        assertEqualsBook(result, NOT_EXISTS_BOOK);
    }

    @DisplayName("correctly delete book with author and genre")
    @Test
    public void shouldCorrectDeleteBookWithAuthorAndGenre() {
        BookDto book = bookService.save(NOT_EXISTS_BOOK);
        assertDoesNotThrow(() -> bookService.delete(book));
    }

    private void assertEqualsBook(BookDto book1, BookDto book2) {
        assertThat(book1).isNotNull()
                .matches(r -> r.getName().equals(book2.getName()))
                .matches(r -> r.getNumberPages().equals(book2.getNumberPages()))
                .matches(r -> r.getYearIssue().equals(book2.getYearIssue()))
                .matches(r -> r.getAuthors().get(0).getAge() == book2.getAuthors().get(0).getAge())
                .matches(r -> r.getAuthors().get(0).getFirstName().equals(book2.getAuthors().get(0).getFirstName()))
                .matches(r -> r.getAuthors().get(0).getLastName().equals(book2.getAuthors().get(0).getLastName()))
                .matches(r -> r.getAuthors().get(0).getYearBirthdate() == book2.getAuthors().get(0).getYearBirthdate())
                .matches(r -> r.getGenres().get(0).getGenre().equals(book2.getGenres().get(0).getGenre()));
    }

    @Test
    public void shouldCorrectReturnEmptyBookDtoIfAuthorNotExists() {
        BookDto expected = BookDto.builder()
                .genres(List.of())
                .authors(List.of()).build();
        BookDto result = bookService.getBookById(1111L);

        assertEquals(expected, result);
    }
}