package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.Utils.*;

@DisplayName("Dao to work with book should")
@DataMongoTest
public class BookRepositoryJpaTest {

    private static final List<Author> EXPECTED_AUTHORS = List.of(
            new Author("100", "Herbert", "Shieldt", 72, 1951),
            new Author("200", "Ivan", "Efremov", 64, 1908),
            new Author("300", "Isaac", "Asimov", 72, 1919),
            new Author("400", "Irvine", "Welsh", 64, 1958),
            new Author("500", "Lyubov", "Voronkova", 70, 1906)
    );

    private static final List<Genre> EXPECTED_GENRES = List.of(
            new Genre("100", "Fiction"),
            new Genre("200", "Novel"),
            new Genre("300", "Thriller"),
            new Genre("400", "Tale"),
            new Genre("500", "Comedy"),
            new Genre("600", "Drama"),
            new Genre("700", "Popular science literature"),
            new Genre("800", "Art and culture"),
            new Genre("900", "Reference books and professional literature"),
            new Genre("1000", "Hobbies, skills"),
            new Genre("1100", "Modern domestic prose")
    );

    private static final Book EXISTING_BOOK = new Book(
            "300",
            "FOUNDATION",
            2022,
            320,
            List.of(
                    new Genre("200", "Novel"),
                    new Genre("600", "Drama"),
                    new Genre("700", "Popular science literature")
            ),
            List.of(
                    new Author("300", "Isaac", "Asimov", 72, 1919)
            ),
            List.of(
                    new Comment("400", "Isaac Asimov Top", "300"),
                    new Comment("500", "The best book in the world", "300")
            )
    );

    private static final List<Book> EXPECTED_BOOK = List.of(
            new Book(
                    "100",
                    "Java. Complete guide",
                    2022,
                    1344,
                    List.of(
                            new Genre("900", "Reference books and professional literature"),
                            new Genre("1000", "Hobbies, skills")
                    ),
                    List.of(
                            new Author("100", "Herbert", "Shieldt", 72, 1951)
                    ),
                    List.of(
                            new Comment("100", "Good Book!", "100"),
                            new Comment("200", "Very Interesting!", "100"),
                            new Comment("300", "I cried when I read it", "100")
                    )
            ),
            new Book(
                    "200",
                    "Starships. Andromeda's nebula",
                    1987,
                    400,
                    List.of(
                            new Genre("200", "Novel"),
                            new Genre("600", "Drama"),
                            new Genre("700", "Popular science literature")
                    ),
                    List.of(
                            new Author("200", "Ivan", "Efremov", 64, 1908)
                    ),
                    List.of(
                            new Comment("600", "I read it, it's cool", "200")
                    )
            ),
            new Book(
                    "300",
                    "FOUNDATION",
                    2022,
                    320,
                    List.of(
                            new Genre("200", "Novel"),
                            new Genre("600", "Drama"),
                            new Genre("700", "Popular science literature")
                    ),
                    List.of(
                            new Author("300", "Isaac", "Asimov", 72, 1919)
                    ),
                    List.of(
                            new Comment("400", "Isaac Asimov Top", "300"),
                            new Comment("500", "The best book in the world", "300")
                    )
            ),
            new Book(
                    "400",
                    "Alice's Adventures in Wonderland",
                    1865,
                    225,
                    null,
                    null,
                    null
            )
    );

    private static final Book NOT_EXISTS_BOOK = new Book(
            null,
            "Son of Zeus",
            2023,
            1024,
            List.of(
                    new Genre("1200", "Modern domestic prose")
            ),
            List.of(
                    new Author(
                            "600", "Lyubov", "Voronkova", 70, 1906
                    )
            ),
            List.of()
    );

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("correctly return the book by id")
    @Test
    public void shouldCorrectReturnBookById() {
        Book book = bookRepository.findById(EXISTING_BOOK.getId()).get();
        assertEqualsBook(EXISTING_BOOK, book);
    }

    @DisplayName("correctly return the books by id")
    @Test
    public void shouldCorrectReturnBooksByIds() {
        List<String> ids = List.of(
                EXPECTED_BOOK.get(0).getId(),
                EXPECTED_BOOK.get(1).getId(),
                EXPECTED_BOOK.get(2).getId(),
                EXPECTED_BOOK.get(3).getId()
        );

        List<Book> result = bookRepository.findByIds(ids);

        assertEqualsBookList(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() {
        List<Book> result = bookRepository.findAll();
        assertEqualsBookList(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly save book")
    @Test
    public void shouldCorrectInsertBook() {
        Book book = bookRepository.save(NOT_EXISTS_BOOK);
        Book result = bookRepository.findById(book.getId()).get();
        bookRepository.delete(book);

        assertEqualsBook(book, result);
    }

    @DisplayName("correctly update book")
    @Test
    public void shouldCorrectUpdateBook() {
        Book exceptedBook = new Book(
                EXISTING_BOOK.getId(), "NEW NAME", 2012, 121,
                List.of(
                        new Genre("100", "Fiction"),
                        new Genre("200", "Novel"),
                        new Genre("300", "Thriller")
                ),
                List.of(
                        new Author("100", "Herbert", "Shieldt", 72, 1951),
                        new Author("200", "Ivan", "Efremov", 64, 1908)
                ),
                List.of(
                        new Comment("400", "New Comment", "300")
                )
        );

        Book book = bookRepository.save(exceptedBook);
        Book result = bookRepository.findById(book.getId()).get();
        bookRepository.save(EXISTING_BOOK);

        assertEqualsBook(exceptedBook, result);
    }

    @DisplayName("correctly delete book")
    @Test
    public void shouldCorrectDeleteBook() {
        String bookId = "200";

        assertThat(bookRepository.findById(bookId))
                .isNotNull();

        bookRepository.deleteById(bookId);

        assertThat(bookRepository.findById(bookId))
                .isEqualTo(Optional.empty());

        bookRepository.save(EXPECTED_BOOK.get(1));
    }

    @DisplayName("correctly return the authors by book id")
    @Test
    public void shouldCorrectReturnAuthorsByBookId() {
        String bookId = "100";
        Book result = bookRepository.findBookWithAuthorsById(bookId);
        assertEqualsAuthorList(List.of(EXPECTED_AUTHORS.get(0)), result.getAuthors());
    }

    @DisplayName("correctly return the authors by book ids")
    @Test
    public void shouldCorrectReturnAuthorsByBookIds() {
        List<String> bookIds = List.of("100", "200", "300");
        List<Book> books = bookRepository.findBookWithAuthorsByIds(bookIds);
        List<Author> result = books.stream()
                .flatMap(b -> b.getAuthors().stream())
                .toList();

        assertEqualsAuthorList(List.of(EXPECTED_AUTHORS.get(0), EXPECTED_AUTHORS.get(1), EXPECTED_AUTHORS.get(2)), result);
    }

    @DisplayName("correctly return the genres by book id")
    @Test
    public void shouldCorrectReturnGenresByBookId() {
        String bookId = "100";
        List<Genre> expected = List.of(EXPECTED_GENRES.get(8), EXPECTED_GENRES.get(9));

        Book result = bookRepository.findBookWithGenresById(bookId);
        assertEqualsGenreList(expected, result.getGenres());
    }

    @DisplayName("correctly return the genres by book ids")
    @Test
    public void shouldCorrectReturnGenresByBookIds() {
        List<String> bookIds = List.of("100", "200");
        List<Genre> expected = List.of(EXPECTED_GENRES.get(8), EXPECTED_GENRES.get(9), EXPECTED_GENRES.get(5), EXPECTED_GENRES.get(6), EXPECTED_GENRES.get(1));

        List<Book> books = bookRepository.findBookWithGenresByIds(bookIds);
        List<Genre> result = books.stream()
                .flatMap(b -> b.getGenres().stream())
                .toList();

        assertEqualsGenreList(expected, result);
    }

    @Test
    public void shouldCorrectReturnEmptyOptionalIfBookNotExists() {
        assertThat(bookRepository.findById("111"))
                .isEqualTo(Optional.empty());
    }
}