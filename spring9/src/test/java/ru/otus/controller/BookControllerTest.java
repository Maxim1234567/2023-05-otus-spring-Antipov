package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Book;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.exception.NotFoundException;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.Utils.assertEqualsBookDto;
import static ru.otus.Utils.assertEqualsCommentListDto;

@DisplayName("Controller to work with book should")
@WebMvcTest(BookController.class)
public class BookControllerTest {
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

    private static final List<GenreDto> EXPECTED_GENRES = List.of(
            new GenreDto(100L, "Fiction"),
            new GenreDto(200L, "Novel"),
            new GenreDto(300L, "Thriller"),
            new GenreDto(400L, "Tale"),
            new GenreDto(500L, "Comedy"),
            new GenreDto(600L, "Drama"),
            new GenreDto(700L, "Popular science literature"),
            new GenreDto(800L, "Art and culture"),
            new GenreDto(900L, "Reference books and professional literature"),
            new GenreDto(1000L, "Hobbies, skills"),
            new GenreDto(1100L, "Modern domestic prose")
    );

    private static final List<AuthorDto> EXPECTED_AUTHORS = List.of(
            new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951),
            new AuthorDto(200L, "Ivan", "Efremov", 64, 1908),
            new AuthorDto(300L, "Isaac", "Asimov", 72, 1919),
            new AuthorDto(400L, "Irvine", "Welsh", 64, 1958),
            new AuthorDto(500L, "Lyubov", "Voronkova", 70, 1906)
    );

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() throws Exception {
        BookDto book1 = EXPECTED_BOOK.get(0);
        BookDto book2 = EXPECTED_BOOK.get(1);
        BookDto book3 = EXPECTED_BOOK.get(2);

        List<BookDto> books = List.of(
                book1, book2, book3
        );

        given(bookService.getAllBooks())
                .willReturn(books);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-book"))
                .andExpect(model().attribute("books", hasSize(3)))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("id", is(book1.getId())),
                                hasProperty("name", is(book1.getName())),
                                hasProperty("numberPages", is(book1.getNumberPages())),
                                hasProperty("yearIssue", is(book1.getYearIssue()))
                        )
                )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("id", is(book2.getId())),
                                hasProperty("name", is(book2.getName())),
                                hasProperty("numberPages", is(book2.getNumberPages())),
                                hasProperty("yearIssue", is(book2.getYearIssue()))
                        )
                )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("id", is(book3.getId())),
                                hasProperty("name", is(book3.getName())),
                                hasProperty("numberPages", is(book3.getNumberPages())),
                                hasProperty("yearIssue", is(book3.getYearIssue()))
                        )
                )));

        verify(bookService, times(1)).getAllBooks();
    }

    @DisplayName("correctly return view by book id")
    @Test
    public void shouldCorrectReturnViewByBookId() throws Exception {
        BookDto book = EXPECTED_BOOK.get(0);

        given(bookService.getBookById(eq(100L)))
                .willReturn(book);

        mvc.perform(get("/book")
                .queryParam("id", "100")
        )
        .andExpect(status().isOk())
        .andExpect(view().name("info-book"))
        .andExpect(model().attribute("book", is(
                allOf(
                        hasProperty("id", is(book.getId())),
                        hasProperty("name", is(book.getName())),
                        hasProperty("numberPages", is(book.getNumberPages())),
                        hasProperty("yearIssue", is(book.getYearIssue())),
                        hasProperty("authors", is(book.getAuthors())),
                        hasProperty("genres", is(book.getGenres())),
                        hasProperty("comments", is(book.getComments()))
                )
        )));
    }

    @DisplayName("correctly add genre")
    @Test
    public void shouldCorrectAddGenre() throws Exception {
        BookDto book = EXPECTED_BOOK.get(0);

        List<GenreDto> genres = List.of(
                EXPECTED_GENRES.get(0), EXPECTED_GENRES.get(1), EXPECTED_GENRES.get(2)
        );

        GenreDto genre1 = book.getGenres().get(0);
        GenreDto genre2 = book.getGenres().get(1);

        given(genreService.getAll())
                .willReturn(genres);

        given(authorService.getAll())
                .willReturn(List.of());

        given(genreService.getGenreById(eq(genre1.getId())))
                .willReturn(genre1);

        given(genreService.getGenreById(genre2.getId()))
                .willReturn(genre2);

        mvc.perform(post("/book/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", Long.toString(book.getId()))
                .param("name", book.getName())
                .param("numberPages", Integer.toString(book.getNumberPages()))
                .param("yearIssue", Integer.toString(book.getYearIssue()))
                .param("addGenre", "")
                .param("genres[0].id", Long.toString(genre1.getId()))
                .param("genres[1].id", Long.toString(genre2.getId()))
        )
        .andExpect(status().isOk())
        .andExpect(view().name("edit-book"))
        .andExpect(model().attribute("book", hasProperty("genres", hasSize(3))))
        .andExpect(model().attribute("book", hasProperty("genres", hasItem(
                allOf(
                        hasProperty("id", is(genre1.getId())),
                        hasProperty("genre", is(genre1.getGenre()))
                )
        ))))
        .andExpect(model().attribute("book", hasProperty("genres", hasItem(
                allOf(
                        hasProperty("id", is(genre2.getId())),
                        hasProperty("genre", is(genre2.getGenre()))
                )
        ))))
        .andExpect(model().attribute("book", hasProperty("genres", hasItem(
                allOf(
                        hasProperty("id", is(100L)),
                        hasProperty("genre", is("Fiction"))
                )
        ))));
    }

    @DisplayName("correctly add author")
    @Test
    public void shouldCorrectAddAuthor() throws Exception {
        BookDto book = EXPECTED_BOOK.get(1);

        List<AuthorDto> authors = List.of(
                EXPECTED_AUTHORS.get(0), EXPECTED_AUTHORS.get(1), EXPECTED_AUTHORS.get(2)
        );

        AuthorDto author1 = book.getAuthors().get(0);

        given(authorService.getAll())
                .willReturn(authors);

        given(genreService.getAll())
                .willReturn(List.of());

        given(authorService.getAuthorById(author1.getId()))
                .willReturn(author1);

        mvc.perform(post("/book/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", Long.toString(book.getId()))
                .param("name", book.getName())
                .param("numberPages", Integer.toString(book.getNumberPages()))
                .param("yearIssue", Integer.toString(book.getYearIssue()))
                .param("addAuthor", "")
                .param("authors[0].id", Long.toString(author1.getId()))
        )
        .andExpect(status().isOk())
        .andExpect(view().name("edit-book"))
        .andExpect(model().attribute("book", hasProperty("authors", hasSize(2))))
        .andExpect(model().attribute("book", hasProperty("authors", hasItem(
                allOf(
                        hasProperty("id", is(author1.getId())),
                        hasProperty("name", is(author1.getName()))
                )
        ))))
        .andExpect(model().attribute("book", hasProperty("authors", hasItem(
                allOf(
                        hasProperty("id", is(100L)),
                        hasProperty("name", is("Herbert Shieldt"))
                )
        ))));
    }

    @DisplayName("correctly remove genre")
    @Test
    public void shouldCorrectRemoveGenre() throws Exception {
        BookDto book = EXPECTED_BOOK.get(0);
        book.setGenres(List.of(
                book.getGenres().get(0), book.getGenres().get(1), EXPECTED_GENRES.get(3)
        ));

        List<GenreDto> genres = List.of(
                EXPECTED_GENRES.get(0), EXPECTED_GENRES.get(1), EXPECTED_GENRES.get(2)
        );

        GenreDto genre1 = book.getGenres().get(0);
        GenreDto genre2 = book.getGenres().get(1);
        GenreDto genre3 = book.getGenres().get(2);

        given(genreService.getAll())
                .willReturn(genres);

        given(authorService.getAll())
                .willReturn(List.of());

        given(genreService.getGenreById(eq(genre1.getId())))
                .willReturn(genre1);

        given(genreService.getGenreById(genre2.getId()))
                .willReturn(genre2);

        mvc.perform(post("/book/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", Long.toString(book.getId()))
                .param("name", book.getName())
                .param("numberPages", Integer.toString(book.getNumberPages()))
                .param("yearIssue", Integer.toString(book.getYearIssue()))
                .param("removeGenre", Long.toString(genre3.getId()))
                .param("genres[0].id", Long.toString(genre1.getId()))
                .param("genres[1].id", Long.toString(genre2.getId()))
                .param("genres[2].id", Long.toString(genre3.getId())))
        .andExpect(status().isOk())
        .andExpect(view().name("edit-book"))
        .andExpect(model().attribute("book", hasProperty("genres", hasSize(2))))
        .andExpect(model().attribute("book", hasProperty("genres", hasItem(
                        allOf(
                                hasProperty("id", is(genre1.getId()))
                        )
                ))))
                .andExpect(model().attribute("book", hasProperty("genres", hasItem(
                        allOf(
                                hasProperty("id", is(genre2.getId()))
                        )
        ))));
    }

    @DisplayName("correctly remove author")
    @Test
    public void shouldCorrectRemoveAuthor() throws Exception {
        BookDto book = EXPECTED_BOOK.get(0);

        book.setAuthors(List.of(
                book.getAuthors().get(0), EXPECTED_AUTHORS.get(1)
        ));

        List<AuthorDto> authors = List.of(
                EXPECTED_AUTHORS.get(0), EXPECTED_AUTHORS.get(1), EXPECTED_AUTHORS.get(2)
        );

        AuthorDto author1 = book.getAuthors().get(0);
        AuthorDto author2 = book.getAuthors().get(1);

        given(genreService.getAll())
                .willReturn(List.of());

        given(authorService.getAll())
                .willReturn(authors);

        given(authorService.getAuthorById(eq(author1.getId())))
                .willReturn(author1);

        given(authorService.getAuthorById(author2.getId()))
                .willReturn(author2);

        mvc.perform(post("/book/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", Long.toString(book.getId()))
                        .param("name", book.getName())
                        .param("numberPages", Integer.toString(book.getNumberPages()))
                        .param("yearIssue", Integer.toString(book.getYearIssue()))
                        .param("removeAuthor", Long.toString(author2.getId()))
                        .param("authors[0].id", Long.toString(author1.getId()))
                        .param("authors[1].id", Long.toString(author2.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-book"))
                .andExpect(model().attribute("book", hasProperty("authors", hasSize(1))))
                .andExpect(model().attribute("book", hasProperty("authors", hasItem(
                        allOf(
                                hasProperty("id", is(author1.getId()))
                        )
                ))));
    }

    @DisplayName("correctly update book")
    @Test
    public void shouldCorrectUpdateBook() throws Exception {
        BookDto book = EXPECTED_BOOK.get(0);

        BookDto updated = BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .yearIssue(book.getYearIssue())
                .numberPages(book.getNumberPages())
                .authors(new ArrayList<>(book.getAuthors()))
                .genres(new ArrayList<>(book.getGenres()))
                .comments(new ArrayList<>(book.getComments()))
                .build();

        updated.setName("Update Name");
        updated.setYearIssue(1970);
        updated.setNumberPages(100);
        updated.getAuthors().add(EXPECTED_AUTHORS.get(3));
        updated.getAuthors().add(EXPECTED_AUTHORS.get(4));
        updated.getGenres().add(EXPECTED_GENRES.get(5));
        updated.getGenres().add(EXPECTED_GENRES.get(6));
        updated.getComments().forEach(c -> c.setBook(updated));

        mvc.perform(post("/book/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", Long.toString(updated.getId()))
                .param("name", updated.getName())
                .param("numberPages", Integer.toString(updated.getNumberPages()))
                .param("yearIssue", Integer.toString(updated.getYearIssue()))
                .param("genres[0].id", Long.toString(updated.getGenres().get(0).getId()))
                .param("genres[1].id", Long.toString(updated.getGenres().get(1).getId()))
                .param("genres[2].id", Long.toString(updated.getGenres().get(2).getId()))
                .param("genres[3].id", Long.toString(updated.getGenres().get(3).getId()))
                .param("authors[0].id", Long.toString(updated.getAuthors().get(0).getId()))
                .param("authors[1].id", Long.toString(updated.getAuthors().get(1).getId()))
                .param("authors[2].id", Long.toString(updated.getAuthors().get(2).getId()))
                .param("comments[0].id", Long.toString(updated.getComments().get(0).getId()))
                .param("comments[0].comments", updated.getComments().get(0).getComments())
                .param("comments[1].id", Long.toString(updated.getComments().get(1).getId()))
                .param("comments[1].comments", updated.getComments().get(1).getComments())
                .param("comments[2].id", Long.toString(updated.getComments().get(2).getId()))
                .param("comments[2].comments", updated.getComments().get(2).getComments()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(redirectedUrl("/"));

        ArgumentCaptor<BookDto> formObjectArgument = ArgumentCaptor.forClass(BookDto.class);
        verify(bookService, times(1)).update(formObjectArgument.capture());

        BookDto formObject = formObjectArgument.getValue();

        assertThat(formObject).isNotNull()
                .matches(b -> b.getId().equals(updated.getId()))
                .matches(b -> b.getName().equals(updated.getName()))
                .matches(b -> b.getYearIssue().equals(updated.getYearIssue()))
                .matches(b -> b.getNumberPages().equals(updated.getNumberPages()));

        assertThat(formObject.getAuthors()).hasSize(3);
        assertThat(formObject.getGenres()).hasSize(4);

        assertEqualsCommentListDto(formObject.getComments(), updated.getComments());
    }

    @DisplayName("return view not found")
    @Test
    public void shouldReturnViewNotFound() throws Exception {
        given(bookService.getBookById(anyLong()))
                .willThrow(NotFoundException.class);

        mvc.perform(get("/book")
                .queryParam("id", "100"))
                .andExpect(status().isOk())
                .andExpect(view().name("not-found-exception"));
    }

    @DisplayName("catch error validation create book")
    @Test
    public void shouldCatchErrorValidationCreateBook() throws Exception {
        BookDto updated = BookDto.builder()
                .id(100L)
                .name("nm")
                .yearIssue(null)
                .numberPages(null)
                .build();

        mvc.perform(post("/book/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", Long.toString(updated.getId()))
                .param("name", updated.getName())
                .param("yearIssue", String.valueOf(updated.getYearIssue()))
                .param("numberPages", String.valueOf(updated.getNumberPages())))
        .andExpect(status().isOk())
        .andExpect(view().name("edit-book"));

        verify(bookService, times(0)).update(ArgumentMatchers.any());
    }
}
