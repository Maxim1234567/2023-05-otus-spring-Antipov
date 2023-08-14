package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.exception.NotFoundException;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.GenreService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.Utils.assertEqualsBookDto;

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

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @DisplayName("correctly return all books")
    @Test
    public void shouldCorrectReturnAllBooks() {
        assertTrue(false);
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
}
