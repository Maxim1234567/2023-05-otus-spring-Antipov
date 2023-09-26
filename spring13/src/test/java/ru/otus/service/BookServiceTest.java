package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.acls.model.MutableAclService;
import ru.otus.convert.BookConvertBookDto;
import ru.otus.convert.BookDtoConvertBook;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.otus.Utils.assertEqualsBookDto;
import static ru.otus.Utils.assertEqualsBookListDto;

@DisplayName("Service to work with book should")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private BookDto bookDto = new BookDto(
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

    private Book book = new Book(
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
            ),
            List.of(
                    new Comment(400L, "Isaac Asimov Top",
                            Book.builder()
                                    .id(300L)
                                    .name("FOUNDATION")
                                    .yearIssue(2022)
                                    .numberPages(320)
                                    .authors(Collections.emptyList())
                                    .genres(Collections.emptyList())
                                    .comments(Collections.emptyList())
                                    .build()),
                    new Comment(500L, "The best book in the world",
                            Book.builder()
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

    private static final List<BookDto> EXPECTED_BOOK_DTO = List.of(
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
                    ),
                    List.of(
                            new Comment(100L, "Good Book!",
                                    Book.builder()
                                            .id(100L)
                                            .name("Java. Complete guide")
                                            .yearIssue(2022)
                                            .numberPages(1344)
                                            .authors(Collections.emptyList())
                                            .genres(Collections.emptyList())
                                            .comments(Collections.emptyList())
                                            .build()),
                            new Comment(200L, "Very Interesting!",
                                    Book.builder()
                                            .id(100L)
                                            .name("Java. Complete guide")
                                            .yearIssue(2022)
                                            .numberPages(1344)
                                            .authors(Collections.emptyList())
                                            .genres(Collections.emptyList())
                                            .comments(Collections.emptyList())
                                            .build()),
                            new Comment(300L, "I cried when I read it",
                                    Book.builder()
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
                    ),
                    List.of(
                            new Comment(600L, "I read it, it's cool",
                                    Book.builder()
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
                    ),
                    List.of(
                            new Comment(400L, "Isaac Asimov Top",
                                    Book.builder()
                                            .id(300L)
                                            .name("FOUNDATION")
                                            .yearIssue(2022)
                                            .numberPages(320)
                                            .authors(Collections.emptyList())
                                            .genres(Collections.emptyList())
                                            .comments(Collections.emptyList())
                                            .build()),
                            new Comment(500L, "The best book in the world",
                                    Book.builder()
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
            new Book(
                    400L,
                    "Alice's Adventures in Wonderland",
                    1865,
                    225,
                    List.of(),
                    List.of(),
                    List.of()
            )
    );

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private BookConvertBookDto convertBookDto;

    @Mock
    private BookDtoConvertBook convertBook;

    @Mock
    private MutableAclService mutableAclService;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookServiceImpl(
                bookRepository,
                authorRepository,
                genreRepository,
                convertBookDto,
                convertBook,
                mutableAclService
        );
    }

    @DisplayName("correctly return book with genres and authors")
    @Test
    public void shouldCorrectReturnBookWithGenreAndAuthors() {
        given(bookRepository.findById(eq(bookDto.getId())))
                .willReturn(Optional.of(book));
        given(convertBookDto.convert(eq(book)))
                .willReturn(bookDto);

        BookDto result = bookService.getBookById(bookDto.getId());

        assertEqualsBookDto(result, bookDto);

        verify(bookRepository, times(1)).findById(eq(book.getId()));
        verify(convertBookDto, times(1)).convert(eq(book));
    }

    @Test
    @DisplayName("should throws NotFoundException if book not exists")
    public void shouldThrowNotFoundExceptionIfBookNotExists() {
        given(bookRepository.findById(eq(bookDto.getId())))
                .willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getBookById(bookDto.getId()));

        verify(bookRepository, times(1)).findById(eq(book.getId()));
        verify(convertBookDto, times(0)).convert(any(Book.class));
    }

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() {
        given(bookRepository.findAll())
                .willReturn(EXPECTED_BOOK);
        given(convertBookDto.convert(EXPECTED_BOOK.get(0)))
                .willReturn(EXPECTED_BOOK_DTO.get(0));
        given(convertBookDto.convert(EXPECTED_BOOK.get(1)))
                .willReturn(EXPECTED_BOOK_DTO.get(1));
        given(convertBookDto.convert(EXPECTED_BOOK.get(2)))
                .willReturn(EXPECTED_BOOK_DTO.get(2));
        given(convertBookDto.convert(EXPECTED_BOOK.get(3)))
                .willReturn(EXPECTED_BOOK_DTO.get(3));

        List<BookDto> results = bookService.getAllBooks();

        assertEqualsBookListDto(results, EXPECTED_BOOK_DTO);

        verify(bookRepository, times(1))
                .findAll();
        verify(convertBookDto, times(1))
                .convert(eq(EXPECTED_BOOK.get(0)));
        verify(convertBookDto, times(1))
                .convert(eq(EXPECTED_BOOK.get(1)));
        verify(convertBookDto, times(1))
                .convert(eq(EXPECTED_BOOK.get(2)));
        verify(convertBookDto, times(1))
                .convert(eq(EXPECTED_BOOK.get(3)));
    }

//    @DisplayName("correctly save book with authors and genres")
//    @Test
    public void shouldCorrectSaveBookWithAuthorAndGenre() {
        given(convertBook.convert(eq(bookDto)))
                .willReturn(book);
        given(authorRepository.findByIds(eq(bookDto.getAuthors().stream().map(AuthorDto::getId).toList())))
                .willReturn(new ArrayList<>(book.getAuthors()));
        given(genreRepository.findByIds(eq(bookDto.getGenres().stream().map(GenreDto::getId).toList())))
                .willReturn(new ArrayList<>(book.getGenres()));
        given(bookRepository.save(eq(book)))
                .willReturn(book);
        given(convertBookDto.convert(eq(book)))
                .willReturn(bookDto);

        BookDto result = bookService.create(bookDto);

        assertEqualsBookDto(result, bookDto);

        verify(convertBook, times(1))
                .convert(eq(bookDto));
        verify(authorRepository, times(1))
                .findByIds(eq(bookDto.getAuthors().stream().map(AuthorDto::getId).toList()));
        verify(genreRepository, times(1))
                .findByIds(eq(bookDto.getGenres().stream().map(GenreDto::getId).toList()));
        verify(bookRepository, times(1))
                .save(eq(book));
        verify(convertBookDto, times(1))
                .convert(eq(book));
    }

    @DisplayName("correctly delete book with author and genre")
    @Test
    public void shouldCorrectDeleteBookWithAuthorAndGenre() {
        bookService.delete(bookDto);

        verify(bookRepository, times(1))
                .deleteById(eq(bookDto.getId()));
    }
}