package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao to work with book should")
@JdbcTest
@Import({BookDaoJdbcImpl.class, GenreDaoJdbcImpl.class, AuthorDaoJdbcImpl.class})
public class BookDaoJdbcTest {
    private static final Book EXISTING_BOOK = new Book(
            300L,
            "FOUNDATION",
            2022,
            320
    );

    private static final Book EXISTING_BOOK_WITHOUT_GENRES_AND_AUTHORS = new Book(
            400L,
            "Alice's Adventures in Wonderland",
            1865,
            225
    );

    private static final List<Book> EXPECTED_BOOK = List.of(
            new Book(
                    100L,
                    "Java. Complete guide",
                    2022,
                    1344
            ),
            new Book(
                    200L,
                    "Starships. Andromeda's nebula",
                    1987,
                    400
            ),
            new Book(
                    300L,
                    "FOUNDATION",
                    2022,
                    320
            ),
            new Book(
                    400L,
                    "Alice's Adventures in Wonderland",
                    1865,
                    225
            )
    );

    private static final Book NOT_EXISTS_BOOK = new Book(
            null,
            "Son of Zeus",
            2023,
            1024
    );

    @Autowired
    private BookDaoJdbcImpl bookDao;

    @Autowired
    private GenreDaoJdbcImpl genreDao;

    @Autowired
    private AuthorDaoJdbcImpl authorDao;

    @DisplayName("correctly return the book by id")
    @Test
    public void shouldCorrectReturnBookById() {
        Book book = bookDao.getById(EXISTING_BOOK.getId());

        assertEquals(EXISTING_BOOK, book);
    }

    @DisplayName("correctly return the books by id")
    @Test
    public void shouldCorrectReturnBooksByIds() {
        List<Long> ids = List.of(
                EXPECTED_BOOK.get(0).getId(), EXPECTED_BOOK.get(1).getId(), EXPECTED_BOOK.get(2).getId(), EXPECTED_BOOK.get(3).getId()
        );

        List<Book> result = bookDao.findAllById(ids);

        assertEqualsList(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly return the books by genre id")
    @Test
    public void shouldCorrectReturnBooksByGenreId() {
        List<Book> excepted = List.of(EXPECTED_BOOK.get(1), EXPECTED_BOOK.get(2));

        List<Book> result = bookDao.findBookByGenreId(600);

        assertEqualsList(excepted, result);
    }

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() {
        List<Book> result = bookDao.getAll();
        assertEqualsList(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly save book")
    @Test
    public void shouldCorrectInsertBook() {
        Book book = bookDao.insert(NOT_EXISTS_BOOK);
        Book result = bookDao.getById(book.getId());

        assertEquals(book, result);
    }

    @DisplayName("correctly save book with genre ids")
    @Test
    public void shouldCorrectSaveBookAndGenres() {
        long bookId = EXISTING_BOOK_WITHOUT_GENRES_AND_AUTHORS.getId();

        List<Genre> emptyGenres = genreDao.findGenreByBookId(bookId);

        List<Long> exceptedGenreId = List.of(100L, 500L, 700L, 1000L);
        bookDao.saveBookAndGenre(bookId, exceptedGenreId);

        List<Genre> result = genreDao.findGenreByBookId(bookId);

        assertTrue(emptyGenres.isEmpty());
        assertEquals(exceptedGenreId, result.stream().map(Genre::getId).toList());
    }

    @DisplayName("correctly save book with author ids")
    @Test
    public void shouldCorrectSaveBookAndAuthors() {
        long bookId = EXISTING_BOOK_WITHOUT_GENRES_AND_AUTHORS.getId();

        List<Author> emptyAuthors = authorDao.findAuthorByBookId(bookId);

        List<Long> exceptedAuthorId = List.of(100L, 400L);
        bookDao.saveBookAndAuthors(bookId, exceptedAuthorId);

        List<Author> result = authorDao.findAuthorByBookId(bookId);

        assertTrue(emptyAuthors.isEmpty());
        assertEquals(exceptedAuthorId, result.stream().map(Author::getId).toList());
    }

    @DisplayName("correctly update book")
    @Test
    public void shouldCorrectUpdateBook() {
        Book exceptedBook = new Book(
                EXISTING_BOOK.getId(), "NEW NAME", 2012, 121
        );

        Book book = bookDao.update(exceptedBook);
        Book result = bookDao.getById(book.getId());

        assertEquals(exceptedBook, result);
    }

    @DisplayName("correctly delete book")
    @Test
    public void shouldCorrectDeleteBook() {
        long bookId = 200L;

        List<Author> authors = authorDao.findAuthorByBookId(bookId);
        List<Genre> genres = genreDao.findGenreByBookId(bookId);

        assertDoesNotThrow(() -> bookDao.getById(bookId));
        assertDoesNotThrow(() -> bookDao.deleteById(bookId));

        List<Author> emptyAuthors = authorDao.findAuthorByBookId(bookId);
        List<Genre> emptyGenres = genreDao.findGenreByBookId(bookId);

        assertFalse(authors.isEmpty());
        assertFalse(genres.isEmpty());

        assertTrue(emptyAuthors.isEmpty());
        assertTrue(emptyGenres.isEmpty());

        assertThrows(EmptyResultDataAccessException.class, () -> bookDao.getById(bookId));
    }

    public void assertEqualsList(List<Book> genres1, List<Book> genres2) {
        assertIterableEquals(
                genres1.stream().sorted(Comparator.comparing(Book::getId)).toList(),
                genres2.stream().sorted(Comparator.comparing(Book::getId)).toList()
        );
    }
}
