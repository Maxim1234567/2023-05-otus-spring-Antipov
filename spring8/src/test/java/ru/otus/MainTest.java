package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.GenreService;
import ru.otus.service.IOService;
import ru.otus.service.LibraryFacade;
import ru.otus.service.ShowDomain;
import ru.otus.service.UserInteraction;
import ru.otus.service.impl.DomainConvertImpl;
import ru.otus.service.impl.IOServiceStreams;
import ru.otus.service.impl.LibraryFacadeImpl;
import ru.otus.service.impl.ShowDomainImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MainTest {
    private static final String TEXT_WILL_BE =
                    "Create genre!\n" +
                    "Create author!\n" +
                    "Create book!\n" +
                    "Show all genres\n" +
                    "100. | Fiction\n" +
                    "200. | Novel\n" +
                    "300. | Thriller\n" +
                    "1. | Psychology\n" +
                    "Choice genre. For exit enter -1!\n" +
                    "Show all authors\n" +
                    "100. | Herbert Shieldt | 1951 | 72\n" +
                    "200. | Ivan Efremov | 1908 | 64\n" +
                    "300. | Isaac Asimov | 1919 | 72\n" +
                    "1. | Jen Sincero | 1965 | 57\n" +
                    "Choice author. For exit enter -1!\n" +
                    "Show all books\n" +
                    "100. | Java. Complete guide | 2022 | 1344\n" +
                    "----------------------------------------\n" +
                    "  100. | Herbert Shieldt | 1951 | 72\n" +
                    "----------------------------------------\n" +
                    "  900. | Reference books and professional literature  1000. | Hobbies, skills----------------------------------------\n" +
                    "  100. | Good Book!  200. | Very Interesting!  300. | I cried when I read it\n" +
                    "\n" +
                    "200. | Starships. Andromeda's nebula | 1987 | 400\n" +
                    "----------------------------------------\n" +
                    "  200. | Ivan Efremov | 1908 | 64\n" +
                    "----------------------------------------\n" +
                    "  200. | Novel  600. | Drama  700. | Popular science literature----------------------------------------\n" +
                    "  600. | I read it, it's cool\n" +
                    "\n" +
                    "300. | FOUNDATION | 2022 | 320\n" +
                    "----------------------------------------\n" +
                    "  300. | Isaac Asimov | 1919 | 72\n" +
                    "----------------------------------------\n" +
                    "  200. | Novel  600. | Drama  700. | Popular science literature----------------------------------------\n" +
                    "  400. | Isaac Asimov Top  500. | The best book in the world\n" +
                    "\n" +
                    "400. | Alice's Adventures in Wonderland | 1865 | 225\n" +
                    "\n" +
                    "\n" +
                    "1. | uni corn | 2023 | 320\n" +
                    "----------------------------------------\n" +
                    "  1. | Jen Sincero | 1965 | 57\n" +
                    "----------------------------------------\n" +
                    "  1. | Psychology\n" +
                    "\n" +
                    "Create comment!\n" +
                    "Show all comments by bookId 1\n" +
                    "1. | Great Book!\n";

    private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private static final GenreDto genre = new GenreDto(
            "1", "Psychology"
    );

    private static final AuthorDto author = new AuthorDto(
            "1", "Jen", "Sincero", 57, 1965
    );

    private static final BookDto book = new BookDto(
            "1", "uni corn", 2023, 320, List.of(), List.of(), List.of()
    );

    private static final CommentDto comment = new CommentDto(
            "1", "Great Book!", "1"
    );

    private static final List<GenreDto> EXPECTED_GENRES_DTO = List.of(
            new GenreDto("100", "Fiction"),
            new GenreDto("200", "Novel"),
            new GenreDto("300", "Thriller"),
            genre
    );

    private static final List<AuthorDto> EXPECTED_AUTHORS_DTO = List.of(
            new AuthorDto("100", "Herbert", "Shieldt", 72, 1951),
            new AuthorDto("200", "Ivan", "Efremov", 64, 1908),
            new AuthorDto("300", "Isaac", "Asimov", 72, 1919),
            author
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
            ),
            book
    );

    @Mock
    private UserInteraction userInteraction;

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;

    @Mock
    private CommentService commentService;

    @Mock
    private BookService bookService;

    private LibraryFacade libraryFacade;

    @BeforeEach
    public void setUp() {
        IOService ioService = new IOServiceStreams(new PrintStream(baos), System.in);

        ShowDomain showDomain = new ShowDomainImpl(
                new DomainConvertImpl(),
                ioService
        );

        libraryFacade = new LibraryFacadeImpl(
                new IOServiceStreams(new PrintStream(baos), System.in),
                authorService,
                genreService,
                bookService,
                commentService,
                showDomain,
                userInteraction
        );

        given(userInteraction.createGenre())
                .willReturn(genre);

        given(genreService.create(any(GenreDto.class)))
                .willReturn(genre);

        given(userInteraction.createAuthor())
                .willReturn(author);

        given(authorService.create(any(AuthorDto.class)))
                .willReturn(author);

        given(genreService.getAll())
                .willReturn(EXPECTED_GENRES_DTO);

        given(genreService.getGenreById(eq("1")))
                .willReturn(genre);

        given(authorService.getAll())
                .willReturn(EXPECTED_AUTHORS_DTO);

        given(authorService.getAuthorById(eq("1")))
                .willReturn(author);

        given(userInteraction.createBook())
                .willReturn(book);

        given(bookService.getAllBooks())
                .willReturn(EXPECTED_BOOK_DTO);

        given(commentService.create(any(CommentDto.class)))
                .willReturn(comment);

        given(userInteraction.createComment())
                .willReturn(comment);

        given(commentService.getAllCommentsByBookId(eq("1")))
                .willReturn(List.of(comment));

        given(userInteraction.getId())
                .willReturn("1", "-1", "1", "-1");
    }

    @Test
    public void integrationTest() {
        libraryFacade.createGenre();

        libraryFacade.createAuthor();

        libraryFacade.createBook();

        libraryFacade.showBooks();

        libraryFacade.createComment("1");

        libraryFacade.showCommentsByBook("1");

        assertEquals(baos.toString().replace("\r", ""), TEXT_WILL_BE);
    }
}
