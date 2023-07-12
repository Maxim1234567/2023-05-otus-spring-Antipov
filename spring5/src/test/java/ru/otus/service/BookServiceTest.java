package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.Utils;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Service to work with book should")
@SpringBootTest
@ActiveProfiles("book")
public class BookServiceTest {
    private static final Book EXISTING_BOOK = new Book(
            300L,
            "FOUNDATION",
            2022,
            320,
            List.of(
                    new Genre(200L, "Novel"),
                    new Genre(600L, "Drama"),
                    new Genre(700L, "Popular science literature")
            ),
            List.of(
                    new Author(300L, "Isaac", "Asimov", 72, 1919)
            )
    );

    private static final List<Book> EXPECTED_BOOK = List.of(
            new Book(
                    100L,
                    "Java. Complete guide",
                    2022,
                    1344,
                    List.of(
                            new Genre(900L, "Reference books and professional literature"),
                            new Genre(1000L, "Hobbies, skills")
                    ),
                    List.of(
                            new Author(100L, "Herbert", "Shieldt", 72, 1951)
                    )
            ),
            new Book(
                    200L,
                    "Starships. Andromeda's nebula",
                    1987,
                    400,
                    List.of(
                            new Genre(200L, "Novel"),
                            new Genre(600L, "Drama"),
                            new Genre(700L, "Popular science literature")
                    ),
                    List.of(
                            new Author(200L, "Ivan", "Efremov", 64, 1908)
                    )
            ),
            new Book(
                    300L,
                    "FOUNDATION",
                    2022,
                    320,
                    List.of(
                            new Genre(200L, "Novel"),
                            new Genre(600L, "Drama"),
                            new Genre(700L, "Popular science literature")
                    ),
                    List.of(
                            new Author(300L, "Isaac", "Asimov", 72, 1919)
                    )
            ),
            new Book(
                    400L,
                    "Alice's Adventures in Wonderland",
                    1865,
                    225,
                    List.of(),
                    List.of()
            )
    );

    private static final Book NOT_EXISTS_BOOK = new Book(
            null,
            "Son of Zeus",
            2023,
            1024,
            List.of(
                    new Genre(1100L, "Modern domestic prose")
            ),
            List.of(
                    new Author(
                            500L, "Lyubov", "Voronkova", 70, 1906
                    )
            )
    );

    @Autowired
    private BookService bookService;

    @DisplayName("correctly return book with genres and authors")
    @Test
    public void shouldCorrectReturnBookWithGenreAndAuthors() {
        Book result = bookService.getBookById(EXISTING_BOOK.getId());
        assertEquals(EXISTING_BOOK, result);
    }

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() {
        List<Book> result = bookService.getAllBooks();
        Utils.assertEqualsBookList(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly save book")
    @Test
    public void shouldCorrectSaveBookAndAuthorAndGenre() {
        Book book = bookService.save(NOT_EXISTS_BOOK);
        Book result = bookService.getBookById(book.getId());
        bookService.delete(book);

        assertEquals(book, result);
    }

    @DisplayName("correctly delete book with author and genre")
    @Test
    public void shouldCorrectDeleteBookWithAuthorAndGenre() {
        Book book = bookService.save(NOT_EXISTS_BOOK);
        assertDoesNotThrow(() -> bookService.delete(book));
    }
}
