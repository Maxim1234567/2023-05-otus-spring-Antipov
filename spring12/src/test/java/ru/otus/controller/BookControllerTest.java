package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.otus.service.BookService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

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

        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books)));

        verify(bookService, times(1))
                .getAllBooks();
    }

    @DisplayName("correctly return book by id")
    @Test
    public void shouldCorrectReturnBookById() throws Exception {
        BookDto book1 = EXPECTED_BOOK.get(0);

        given(bookService.getBookById(eq(book1.getId())))
                .willReturn(book1);

        mvc.perform(get("/api/book/" + book1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book1)));

        verify(bookService, times(1))
                .getBookById(eq(book1.getId()));
    }

    @DisplayName("throw NotFoundException book not exist id")
    @Test
    public void shouldThrowNotFoundBookNotExistId() throws Exception {
        long notExistsBookId = 1111L;

        given(bookService.getBookById(eq(notExistsBookId)))
                .willThrow(NotFoundException.class);

        mvc.perform(get("/api/book/" + notExistsBookId))
                .andExpect(status().isNotFound());

        verify(bookService, times(1))
                .getBookById(eq(notExistsBookId));
    }

    @DisplayName("correctly correct create book")
    @Test
    public void shouldCorrectCreateBook() throws Exception {
        BookDto added = EXPECTED_BOOK.get(0);

        given(bookService.create(any(BookDto.class)))
                .willReturn(added);

        mvc.perform(post("/api/book")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(added))
                ).andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(added)));

        verify(bookService, times(1))
                .create(any(BookDto.class));
    }

    @DisplayName("correctly correct update book")
    @Test
    public void shouldCorrectUpdateBook() throws Exception {
        BookDto update = EXPECTED_BOOK.get(0);

        given(bookService.update(any(BookDto.class)))
                .willReturn(update);

        mvc.perform(put("/api/book/" + update.getId())
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(update))
                ).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(update)));

        verify(bookService, times(1))
                .update(any(BookDto.class));
    }

    @DisplayName("catch error validation create book")
//    @Test
    public void shouldCatchErrorValidationCreateBook() throws Exception {
        BookDto added = BookDto.builder()
                .name("12")
                .numberPages(null)
                .yearIssue(null)
                .authors(List.of())
                .genres(List.of())
                .comments(List.of())
                .build();

        mvc.perform(post("/api/book")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(added))
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("Validation Error"));

        verify(bookService, times(0))
                .create(any(BookDto.class));
    }

    @DisplayName("catch error validation update book")
//    @Test
    public void shouldCatchErrorValidationUpdateBook() throws Exception {
        BookDto update = BookDto.builder()
                .id(100L)
                .name("12")
                .numberPages(null)
                .yearIssue(null)
                .authors(List.of())
                .genres(List.of())
                .comments(List.of())
                .build();

        mvc.perform(put("/api/book")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(update))
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("Validation Error"));

        verify(bookService, times(0))
                .update(any(BookDto.class));
    }

    @DisplayName("correctly delete book")
    @Test
    public void shouldCorrectDeleteBook() throws Exception {
        BookDto book = EXPECTED_BOOK.get(0);

        given(bookService.getBookById(eq(book.getId())))
                .willReturn(book);

        mvc.perform(delete("/api/book/" + book.getId()))
                .andExpect(status().isNoContent());

        verify(bookService, times(1))
                .getBookById(eq(book.getId()));
        verify(bookService, times(1))
                .delete(eq(book));
    }
}