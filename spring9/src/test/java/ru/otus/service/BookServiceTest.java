package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.exception.NotFoundException;
import ru.otus.exception.ValidationErrorException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.Utils.*;

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
                    new CommentDto(400L, "Isaac Asimov Top",
                            BookDto.builder()
                                    .id(300L)
                                    .name("FOUNDATION")
                                    .yearIssue(2022)
                                    .numberPages(320)
                                    .authors(Collections.emptyList())
                                    .genres(Collections.emptyList())
                                    .comments(Collections.emptyList())
                                    .build()),
                    new CommentDto(500L, "The best book in the world",
                            BookDto.builder()
                                    .id(300L)
                                    .name("FOUNDATION")
                                    .yearIssue(2022)
                                    .numberPages(320)
                                    .authors(Collections.emptyList())
                                    .genres(Collections.emptyList())
                                    .comments(Collections.emptyList())
                                    .build())
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
                            new CommentDto(100L, "Good Book!",
                                    BookDto.builder()
                                            .id(100L)
                                            .name("Java. Complete guide")
                                            .yearIssue(2022)
                                            .numberPages(1344)
                                            .authors(Collections.emptyList())
                                            .genres(Collections.emptyList())
                                            .comments(Collections.emptyList())
                                            .build()),
                            new CommentDto(200L, "Very Interesting!",
                                    BookDto.builder()
                                            .id(100L)
                                            .name("Java. Complete guide")
                                            .yearIssue(2022)
                                            .numberPages(1344)
                                            .authors(Collections.emptyList())
                                            .genres(Collections.emptyList())
                                            .comments(Collections.emptyList())
                                            .build()),
                            new CommentDto(300L, "I cried when I read it",
                                    BookDto.builder()
                                            .id(100L)
                                            .name("Java. Complete guide")
                                            .yearIssue(2022)
                                            .numberPages(1344)
                                            .authors(Collections.emptyList())
                                            .genres(Collections.emptyList())
                                            .comments(Collections.emptyList())
                                            .build())
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
                            new CommentDto(600L, "I read it, it's cool",
                                    BookDto.builder()
                                            .id(200L)
                                            .name("Starships. Andromeda's nebula")
                                            .yearIssue(1987)
                                            .numberPages(400)
                                            .authors(Collections.emptyList())
                                            .genres(Collections.emptyList())
                                            .comments(Collections.emptyList())
                                            .build())
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
                            new CommentDto(400L, "Isaac Asimov Top",
                                    BookDto.builder()
                                            .id(300L)
                                            .name("FOUNDATION")
                                            .yearIssue(2022)
                                            .numberPages(320)
                                            .authors(Collections.emptyList())
                                            .genres(Collections.emptyList())
                                            .comments(Collections.emptyList())
                                            .build()),
                            new CommentDto(500L, "The best book in the world",
                                    BookDto.builder()
                                            .id(300L)
                                            .name("FOUNDATION")
                                            .yearIssue(2022)
                                            .numberPages(320)
                                            .authors(Collections.emptyList())
                                            .genres(Collections.emptyList())
                                            .comments(Collections.emptyList())
                                            .build())
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

    private static final BookDto NOT_EXISTS_BOOK_WITH_AUTHOR_ID_NULL = new BookDto(
            null,
            "Son of Zeus",
            2023,
            1024,
            List.of(),
            List.of(
                    new AuthorDto(
                            null, "Lyubov", "Voronkova", 70, 1906
                    )
            ),
            List.of()
    );

    private static final BookDto NOT_EXISTS_BOOK_WITH_GENRE_ID_NULL = new BookDto(
            null,
            "Son of Zeus",
            2023,
            1024,
            List.of(
                    new GenreDto(null, "Modern domestic prose")
            ),
            List.of(),
            List.of()
    );

    private static final BookDto NOT_EXISTS_BOOK_WITH_NOT_EXISTS_ID_AUTHOR = new BookDto(
            null,
            "Son of Zeus",
            2023,
            1024,
            List.of(),
            List.of(
                    new AuthorDto(
                            222L, "Lyubov", "Voronkova", 70, 1906
                    )
            ),
            List.of()
    );

    private static final BookDto NOT_EXISTS_BOOK_WITH_NOT_EXISTS_ID_GENRE = new BookDto(
            null,
            "Son of Zeus",
            2023,
            1024,
            List.of(
                    new GenreDto(222L, "Modern domestic prose")
            ),
            List.of(),
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

    @DisplayName("throws ValidationError if author id is null")
    @Test
    public void shouldThrowsIfAuthorIdIsNull() {
        assertThrows(ValidationErrorException.class, () -> bookService.create(NOT_EXISTS_BOOK_WITH_AUTHOR_ID_NULL));
    }

    @DisplayName("throws ValidationError if genre id is null")
    @Test
    public void shouldThrowsIfGenreIdIsNull() {
        assertThrows(ValidationErrorException.class, () -> bookService.create(NOT_EXISTS_BOOK_WITH_GENRE_ID_NULL));
    }

    @DisplayName("throws NotFoundException if author id is not exists")
    @Test
    public void shouldThrowsIfAuthorIdIsNotExists() {
        assertThrows(NotFoundException.class, () -> bookService.create(NOT_EXISTS_BOOK_WITH_NOT_EXISTS_ID_AUTHOR));
    }

    @DisplayName("throws NotFoundException if genre id is not exists")
    @Test
    public void shouldThrowsIfGenreIdIsNotExists() {
        assertThrows(NotFoundException.class, () -> bookService.create(NOT_EXISTS_BOOK_WITH_NOT_EXISTS_ID_GENRE));
    }

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() {
        List<BookDto> result = bookService.getAllBooks();
        assertEqualsBookListDto(EXPECTED_BOOK, result);
    }

    @DisplayName("correctly delete book with author and genre")
    @Test
    public void shouldCorrectDeleteBookWithAuthorAndGenre() {
        BookDto book = bookService.create(NOT_EXISTS_BOOK);
        assertDoesNotThrow(() -> bookService.delete(book));
        assertThrows(NotFoundException.class, () -> bookService.getBookById(book.getId()));
    }

    @DisplayName("correctly update book")
    @Test
    public void shouldCorrectUpdateBook() {
        BookDto exceptedBook = new BookDto(
                EXISTING_BOOK.getId(), "NEW NAME", 2012, 121,
                List.of(
                        new GenreDto(100L, "Fiction"),
                        new GenreDto(200L, "Novel"),
                        new GenreDto(300L, "Thriller")
                ),
                List.of(
                        new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951),
                        new AuthorDto(200L, "Ivan", "Efremov", 64, 1908)
                ),
                List.of(
                        new CommentDto(400L, "New Comment",
                                BookDto.builder()
                                        .id(300L)
                                        .name("NEW NAME")
                                        .yearIssue(2012)
                                        .numberPages(121)
                                        .authors(Collections.emptyList())
                                        .genres(Collections.emptyList())
                                        .comments(Collections.emptyList())
                                        .build())
                )
        );

        BookDto book = bookService.update(exceptedBook);
        BookDto result = bookService.getBookById(book.getId());

        assertEqualsBookDto(exceptedBook, result);
    }

    @Test
    @DisplayName("should throws NotFoundException if book not exists")
    public void shouldThrowNotFoundExceptionIfBookNotExists() {
        assertThrows(NotFoundException.class, () -> bookService.getBookById(1111L));
    }
}