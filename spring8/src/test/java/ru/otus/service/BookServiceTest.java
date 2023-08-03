package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.Utils.assertEqualsBookDto;
import static ru.otus.Utils.assertEqualsBookListDto;

@DisplayName("Service to work with book should")
@SpringBootTest
@Transactional
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
            ),
            List.of(
                    new CommentDto(400L, "Isaac Asimov Top", 300L),
                    new CommentDto(500L, "The best book in the world", 300L)
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
                    ),
                    List.of(
                            new CommentDto(100L, "Good Book!", 100L),
                            new CommentDto(200L, "Very Interesting!", 100L),
                            new CommentDto(300L, "I cried when I read it", 100L)
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
                    ),
                    List.of(
                            new CommentDto(600L, "I read it, it's cool", 200L)
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
                    ),
                    List.of(
                            new CommentDto(400L, "Isaac Asimov Top", 300L),
                            new CommentDto(500L, "The best book in the world", 300L)
                    )
            ),
            new BookDto(
                    400L,
                    "Alice's Adventures in Wonderland",
                    1865,
                    225,
                    List.of(),
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
            ),
            List.of()
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
            ),
            List.of()
    );

    @Autowired
    private BookService bookService;

    @DisplayName("correctly return book with genres and authors")
    @Test
    public void shouldCorrectReturnBookWithGenreAndAuthors() {
        BookDto result = bookService.getBookById(EXISTING_BOOK.getId());
        assertEqualsBookDto(EXISTING_BOOK, result);
    }

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() {
        List<BookDto> result = bookService.getAllBooks();
        assertEqualsBookListDto(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly save book with not exists authors and genres")
    @Test
    public void shouldCorrectSaveBookWithNotExistsAuthorAndGenre() {
        BookDto book = bookService.save(NOT_EXISTS_BOOK_WITH_NOT_EXISTS_AUTHOR_AND_GENRE);
        BookDto result = bookService.getBookById(book.getId());
        bookService.delete(book);

        assertEqualsBookDto(result, book);
    }

    @DisplayName("correctly save book with authors and genres")
    @Test
    public void shouldCorrectSaveBookWithAuthorAndGenre() {
        BookDto book = bookService.save(NOT_EXISTS_BOOK);
        BookDto result = bookService.getBookById(book.getId());
        bookService.delete(book);

        assertEqualsBookDto(result, book);
    }

    @DisplayName("correctly delete book with author and genre")
    @Test
    public void shouldCorrectDeleteBookWithAuthorAndGenre() {
        BookDto book = bookService.save(NOT_EXISTS_BOOK);
        assertDoesNotThrow(() -> bookService.delete(book));
    }

    @DisplayName("correctly update book")
    @Test
    public void shouldCorrectUpdateBook() {

    }

    @Test
    @DisplayName("should throws NotFoundException if book not exists")
    public void shouldThrowNotFoundExceptionIfBookNotExists() {
        assertThrows(NotFoundException.class, () -> bookService.getBookById(1111L));
    }
}