package ru.otus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Controller to work with book should")
@WebMvcTest(BookController.class)
public class BookControllerTest {
    private static final String JSON_BOOKS =
            "[\n" +
            "    {\n" +
            "        \"id\": 100,\n" +
            "        \"name\": \"Java. Complete guide\",\n" +
            "        \"yearIssue\": 2022,\n" +
            "        \"numberPages\": 1344,\n" +
            "        \"genres\": [\n" +
            "            {\n" +
            "                \"id\": 900,\n" +
            "                \"genre\": \"Reference books and professional literature\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 1000,\n" +
            "                \"genre\": \"Hobbies, skills\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"authors\": [\n" +
            "            {\n" +
            "                \"id\": 100,\n" +
            "                \"firstName\": \"Herbert\",\n" +
            "                \"lastName\": \"Shieldt\",\n" +
            "                \"age\": 72,\n" +
            "                \"yearBirthdate\": 1951,\n" +
            "                \"name\": \"Herbert Shieldt\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"comments\": [\n" +
            "            {\n" +
            "                \"id\": 100,\n" +
            "                \"comments\": \"Good Book!\",\n" +
            "                \"book\": {\n" +
            "                    \"id\": 100,\n" +
            "                    \"name\": \"Java. Complete guide\",\n" +
            "                    \"yearIssue\": 2022,\n" +
            "                    \"numberPages\": 1344,\n" +
            "                    \"genres\": [],\n" +
            "                    \"authors\": [],\n" +
            "                    \"comments\": []\n" +
            "                }\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 200,\n" +
            "                \"comments\": \"Very Interesting!\",\n" +
            "                \"book\": {\n" +
            "                    \"id\": 100,\n" +
            "                    \"name\": \"Java. Complete guide\",\n" +
            "                    \"yearIssue\": 2022,\n" +
            "                    \"numberPages\": 1344,\n" +
            "                    \"genres\": [],\n" +
            "                    \"authors\": [],\n" +
            "                    \"comments\": []\n" +
            "                }\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 300,\n" +
            "                \"comments\": \"I cried when I read it\",\n" +
            "                \"book\": {\n" +
            "                    \"id\": 100,\n" +
            "                    \"name\": \"Java. Complete guide\",\n" +
            "                    \"yearIssue\": 2022,\n" +
            "                    \"numberPages\": 1344,\n" +
            "                    \"genres\": [],\n" +
            "                    \"authors\": [],\n" +
            "                    \"comments\": []\n" +
            "                }\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 200,\n" +
            "        \"name\": \"Starships. Andromeda's nebula\",\n" +
            "        \"yearIssue\": 1987,\n" +
            "        \"numberPages\": 400,\n" +
            "        \"genres\": [\n" +
            "            {\n" +
            "                \"id\": 200,\n" +
            "                \"genre\": \"Novel\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 600,\n" +
            "                \"genre\": \"Drama\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 700,\n" +
            "                \"genre\": \"Popular science literature\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"authors\": [\n" +
            "            {\n" +
            "                \"id\": 200,\n" +
            "                \"firstName\": \"Ivan\",\n" +
            "                \"lastName\": \"Efremov\",\n" +
            "                \"age\": 64,\n" +
            "                \"yearBirthdate\": 1908,\n" +
            "                \"name\": \"Ivan Efremov\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"comments\": [\n" +
            "            {\n" +
            "                \"id\": 600,\n" +
            "                \"comments\": \"I read it, it's cool\",\n" +
            "                \"book\": {\n" +
            "                    \"id\": 200,\n" +
            "                    \"name\": \"Starships. Andromeda's nebula\",\n" +
            "                    \"yearIssue\": 1987,\n" +
            "                    \"numberPages\": 400,\n" +
            "                    \"genres\": [],\n" +
            "                    \"authors\": [],\n" +
            "                    \"comments\": []\n" +
            "                }\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 300,\n" +
            "        \"name\": \"FOUNDATION\",\n" +
            "        \"yearIssue\": 2022,\n" +
            "        \"numberPages\": 320,\n" +
            "        \"genres\": [\n" +
            "            {\n" +
            "                \"id\": 200,\n" +
            "                \"genre\": \"Novel\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 600,\n" +
            "                \"genre\": \"Drama\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 700,\n" +
            "                \"genre\": \"Popular science literature\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"authors\": [\n" +
            "            {\n" +
            "                \"id\": 300,\n" +
            "                \"firstName\": \"Isaac\",\n" +
            "                \"lastName\": \"Asimov\",\n" +
            "                \"age\": 72,\n" +
            "                \"yearBirthdate\": 1919,\n" +
            "                \"name\": \"Isaac Asimov\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"comments\": [\n" +
            "            {\n" +
            "                \"id\": 400,\n" +
            "                \"comments\": \"Isaac Asimov Top\",\n" +
            "                \"book\": {\n" +
            "                    \"id\": 300,\n" +
            "                    \"name\": \"FOUNDATION\",\n" +
            "                    \"yearIssue\": 2022,\n" +
            "                    \"numberPages\": 320,\n" +
            "                    \"genres\": [],\n" +
            "                    \"authors\": [],\n" +
            "                    \"comments\": []\n" +
            "                }\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 500,\n" +
            "                \"comments\": \"The best book in the world\",\n" +
            "                \"book\": {\n" +
            "                    \"id\": 300,\n" +
            "                    \"name\": \"FOUNDATION\",\n" +
            "                    \"yearIssue\": 2022,\n" +
            "                    \"numberPages\": 320,\n" +
            "                    \"genres\": [],\n" +
            "                    \"authors\": [],\n" +
            "                    \"comments\": []\n" +
            "                }\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "]";

    private static final String JSON_BOOK =
            "{\n" +
            "    \"id\": 100,\n" +
            "    \"name\": \"Java. Complete guide\",\n" +
            "    \"yearIssue\": 2022,\n" +
            "    \"numberPages\": 1344,\n" +
            "    \"genres\": [\n" +
            "        {\n" +
            "            \"id\": 900,\n" +
            "            \"genre\": \"Reference books and professional literature\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 1000,\n" +
            "            \"genre\": \"Hobbies, skills\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"authors\": [\n" +
            "        {\n" +
            "            \"id\": 100,\n" +
            "            \"firstName\": \"Herbert\",\n" +
            "            \"lastName\": \"Shieldt\",\n" +
            "            \"age\": 72,\n" +
            "            \"yearBirthdate\": 1951,\n" +
            "            \"name\": \"Herbert Shieldt\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"comments\": [\n" +
            "        {\n" +
            "            \"id\": 100,\n" +
            "            \"comments\": \"Good Book!\",\n" +
            "            \"book\": {\n" +
            "                \"id\": 100,\n" +
            "                \"name\": \"Java. Complete guide\",\n" +
            "                \"yearIssue\": 2022,\n" +
            "                \"numberPages\": 1344,\n" +
            "                \"genres\": [],\n" +
            "                \"authors\": [],\n" +
            "                \"comments\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 200,\n" +
            "            \"comments\": \"Very Interesting!\",\n" +
            "            \"book\": {\n" +
            "                \"id\": 100,\n" +
            "                \"name\": \"Java. Complete guide\",\n" +
            "                \"yearIssue\": 2022,\n" +
            "                \"numberPages\": 1344,\n" +
            "                \"genres\": [],\n" +
            "                \"authors\": [],\n" +
            "                \"comments\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 300,\n" +
            "            \"comments\": \"I cried when I read it\",\n" +
            "            \"book\": {\n" +
            "                \"id\": 100,\n" +
            "                \"name\": \"Java. Complete guide\",\n" +
            "                \"yearIssue\": 2022,\n" +
            "                \"numberPages\": 1344,\n" +
            "                \"genres\": [],\n" +
            "                \"authors\": [],\n" +
            "                \"comments\": []\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @DisplayName("correctly return all books")
    @WithMockUser(
            username = "user",
            authorities = "USER"
    )
    @Test
    public void shouldCorrectReturnAllBooks() throws Exception {
        BookDto book1 = new BookDto(
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
        );

        BookDto book2 = new BookDto(
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
        );

        BookDto book3 = new BookDto(
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

        List<BookDto> books = List.of(
                book1, book2, book3
        );

        given(bookService.getAllBooks())
                .willReturn(books);

        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_BOOKS));

        verify(bookService, times(1))
                .getAllBooks();
    }

    @DisplayName("correctly return book by id")
    @WithMockUser(
            username = "user",
            authorities = "USER"
    )
    @Test
    public void shouldCorrectReturnBookById() throws Exception {
        BookDto book1 = new BookDto(
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
        );

        given(bookService.getBookById(eq(book1.getId())))
                .willReturn(book1);

        mvc.perform(get("/api/book/" + book1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_BOOK));

        verify(bookService, times(1))
                .getBookById(eq(book1.getId()));
    }

    @DisplayName("throw NotFoundException book not exist id")
    @WithMockUser(
            username = "user",
            authorities = "USER"
    )
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
    @WithMockUser(
            username = "user",
            authorities = "USER"
    )
    @Test
    public void shouldCorrectCreateBook() throws Exception {
        BookDto added = new BookDto(
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
        );;

        given(bookService.create(any(BookDto.class)))
                .willReturn(added);

        mvc.perform(post("/api/book")
                        .with(csrf())
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(added))
                ).andExpect(status().isCreated())
                .andExpect(content().json(JSON_BOOK));

        verify(bookService, times(1))
                .create(any(BookDto.class));
    }

    @DisplayName("correctly correct update book")
    @WithMockUser(
            username = "user",
            authorities = "USER"
    )
    @Test
    public void shouldCorrectUpdateBook() throws Exception {
        BookDto update = new BookDto(
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
        );

        given(bookService.update(any(BookDto.class)))
                .willReturn(update);

        mvc.perform(put("/api/book/" + update.getId())
                        .with(csrf())
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(update))
                ).andExpect(status().isOk())
                .andExpect(content().json(JSON_BOOK));

        verify(bookService, times(1))
                .update(any(BookDto.class));
    }

    @DisplayName("correctly delete book")
    @WithMockUser(
            username = "user",
            authorities = "USER"
    )
    @Test
    public void shouldCorrectDeleteBook() throws Exception {
        BookDto book = new BookDto(
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
        );

        given(bookService.getBookById(eq(book.getId())))
                .willReturn(book);

        mvc.perform(delete("/api/book/" + book.getId()).with(csrf()))
                .andExpect(status().isNoContent());

        verify(bookService, times(1))
                .getBookById(eq(book.getId()));
        verify(bookService, times(1))
                .delete(eq(book));
    }
}