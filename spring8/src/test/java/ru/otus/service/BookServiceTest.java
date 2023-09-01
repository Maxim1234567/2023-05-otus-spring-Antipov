package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.impl.BookServiceImpl;

import java.util.ArrayList;
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
            "300",
            "FOUNDATION",
            2022,
            320,
            List.of(
                    new GenreDto("200", "Novel"),
                    new GenreDto("600", "Drama"),
                    new GenreDto("700", "Popular science literature")
            ),
            List.of(
                    new AuthorDto("300", "Isaac", "Asimov", 72, 1919)
            ),
            List.of(
                    new CommentDto("400", "Isaac Asimov Top", "300"),
                    new CommentDto("500", "The best book in the world", "300")
            )
    );

    private Book book = new Book(
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

    private static final List<BookDto> EXPECTED_BOOK_DTO = List.of(
            new BookDto(
                    "100",
                    "Java. Complete guide",
                    2022,
                    1344,
                    List.of(
                            new GenreDto("900", "Reference books and professional literature"),
                            new GenreDto("1000", "Hobbies, skills")
                    ),
                    List.of(
                            new AuthorDto("100", "Herbert", "Shieldt", 72, 1951)
                    ),
                    List.of(
                            new CommentDto("100", "Good Book!", "100"),
                            new CommentDto("200", "Very Interesting!", "100"),
                            new CommentDto("300", "I cried when I read it", "100")
                    )
            ),
            new BookDto(
                    "200",
                    "Starships. Andromeda's nebula",
                    1987,
                    400,
                    List.of(
                            new GenreDto("200", "Novel"),
                            new GenreDto("600", "Drama"),
                            new GenreDto("700", "Popular science literature")
                    ),
                    List.of(
                            new AuthorDto("200", "Ivan", "Efremov", 64, 1908)
                    ),
                    List.of(
                            new CommentDto("600", "I read it, it's cool", "200")
                    )
            ),
            new BookDto(
                    "300",
                    "FOUNDATION",
                    2022,
                    320,
                    List.of(
                            new GenreDto("200", "Novel"),
                            new GenreDto("600", "Drama"),
                            new GenreDto("700", "Popular science literature")
                    ),
                    List.of(
                            new AuthorDto("300", "Isaac", "Asimov", 72, 1919)
                    ),
                    List.of(
                            new CommentDto("400", "Isaac Asimov Top", "300"),
                            new CommentDto("500", "The best book in the world", "300")
                    )
            ),
            new BookDto(
                    "400",
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
    private CommentRepository commentRepository;

    @Mock
    private BookConvertBookDto convertBookDto;

    @Mock
    private BookDtoConvertBook convertBook;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookServiceImpl(
                bookRepository,
                authorRepository,
                genreRepository,
                commentRepository,
                convertBookDto,
                convertBook
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

    @DisplayName("correctly save book with authors and genres")
    @Test
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