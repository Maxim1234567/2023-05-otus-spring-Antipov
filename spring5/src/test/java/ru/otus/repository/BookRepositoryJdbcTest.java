package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.Utils;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao to work with book should")
@JdbcTest
@Import({BookRepositoryJdbcImpl.class, AuthorRepositoryJdbcImpl.class, GenreRepositoryJdbcImpl.class})
public class BookRepositoryJdbcTest {
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
    private BookRepositoryJdbcImpl bookDao;

    @DisplayName("correctly return the book by id")
    @Test
    public void shouldCorrectReturnBookById() {
        Book book = bookDao.findById(EXISTING_BOOK.getId());
        assertEquals(EXISTING_BOOK, book);
    }

    @DisplayName("correctly return the books by id")
    @Test
    public void shouldCorrectReturnBooksByIds() {
        List<Long> ids = List.of(
                EXPECTED_BOOK.get(0).getId(),
                EXPECTED_BOOK.get(1).getId(),
                EXPECTED_BOOK.get(2).getId(),
                EXPECTED_BOOK.get(3).getId()
        );

        List<Book> result = bookDao.findByIds(ids);

        Utils.assertEqualsBookList(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() {
        List<Book> result = bookDao.getAllBooks();
        Utils.assertEqualsBookList(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly save book")
    @Test
    public void shouldCorrectInsertBook() {
        Book book = bookDao.insert(NOT_EXISTS_BOOK);
        Book result = bookDao.findById(book.getId());

        assertEquals(book, result);
    }

    @DisplayName("correctly update book")
    @Test
    public void shouldCorrectUpdateBook() {
        Book exceptedBook = new Book(
                EXISTING_BOOK.getId(), "NEW NAME", 2012, 121,
                List.of(new Genre(100L, "Fiction"), new Genre(200L, "Novel"), new Genre(300L, "Thriller")),
                List.of(
                        new Author(100L, "Herbert", "Shieldt", 72, 1951),
                        new Author(200L, "Ivan", "Efremov", 64, 1908)
                )
        );

        Book book = bookDao.update(exceptedBook);
        Book result = bookDao.findById(book.getId());

        assertEquals(exceptedBook, result);
    }

    @DisplayName("correctly delete book")
    @Test
    public void shouldCorrectDeleteBook() {
        long bookId = 200L;

        assertDoesNotThrow(() -> bookDao.findById(bookId));
        assertDoesNotThrow(() -> bookDao.deleteById(bookId));

        assertThrows(EmptyResultDataAccessException.class, () -> bookDao.findById(bookId));
    }
}
