package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.Utils.assertEqualsBookDto;

@DisplayName("Controller to work with comment should")
@WebMvcTest(CommentController.class)
public class CommentControllerTest {
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
    private CommentService commentService;

    @DisplayName("correctly get and put book in model")
    @Test
    public void shouldCorrectGetAndPutBookInModel() throws Exception {
        BookDto book = EXPECTED_BOOK.get(0);

        given(bookService.getBookById(eq(book.getId())))
                .willReturn(book);

        mvc.perform(get("/comment/create")
                .queryParam("id", Long.toString(book.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("create-comment"));

        verify(bookService, times(1)).getBookById(eq(book.getId()));
    }

    @DisplayName("correctly create comment")
    @Test
    public void shouldCorrectCreateComment() throws Exception {
        BookDto book = EXPECTED_BOOK.get(0);

        given(bookService.getBookById(eq(book.getId())))
                .willReturn(book);

        CommentDto added = CommentDto.builder()
                .comments("New Comments")
                .book(book)
                .build();

        mvc.perform(post("/comment/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("book.id", Long.toString(book.getId()))
                .param("book.name", book.getName())
                .param("comments", added.getComments()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(redirectedUrl("/"));

        ArgumentCaptor<CommentDto> formObjectArgument = ArgumentCaptor.forClass(CommentDto.class);
        verify(commentService, times(1)).create(formObjectArgument.capture());

        CommentDto formObject = formObjectArgument.getValue();

        assertThat(formObject).isNotNull()
                .matches(c -> c.getComments().equals(added.getComments()));

        assertEqualsBookDto(added.getBook(), formObject.getBook());
    }

    @DisplayName("catch error validation create comment")
    @Test
    public void shouldCatchErrorValidationCreateComment() throws Exception {
        BookDto book = EXPECTED_BOOK.get(0);

        CommentDto added = CommentDto.builder()
                .comments("12")
                .book(book)
                .build();

        mvc.perform(post("/comment/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("book.id", Long.toString(book.getId()))
                .param("book.name", book.getName())
                .param("comments", added.getComments()))
        .andExpect(status().isOk())
        .andExpect(view().name("create-comment"));

        verify(bookService, times(0)).getBookById(anyLong());
        verify(commentService, times(0)).create(ArgumentMatchers.any());
    }
}
