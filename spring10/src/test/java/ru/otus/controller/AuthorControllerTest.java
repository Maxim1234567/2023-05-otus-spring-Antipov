package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.dto.AuthorDto;
import ru.otus.service.AuthorService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@DisplayName("Controller to work with author should")
public class AuthorControllerTest {
    private static final List<AuthorDto> EXPECTED_AUTHORS = List.of(
            new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951),
            new AuthorDto(200L, "Ivan", "Efremov", 64, 1908),
            new AuthorDto(300L, "Isaac", "Asimov", 72, 1919),
            new AuthorDto(400L, "Irvine", "Welsh", 64, 1958),
            new AuthorDto(500L, "Lyubov", "Voronkova", 70, 1906)
    );

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @DisplayName("correctly return all author")
    @Test
    public void shouldCorrectReturnAllAuthor() throws Exception {
        AuthorDto author1 = EXPECTED_AUTHORS.get(0);
        AuthorDto author2 = EXPECTED_AUTHORS.get(1);
        AuthorDto author3 = EXPECTED_AUTHORS.get(2);

        List<AuthorDto> authors = List.of(
                author1, author2, author3
        );

        given(authorService.getAll())
                .willReturn(authors);

        mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(authors)));

        verify(authorService, times(1))
                .getAll();
    }

    @DisplayName("correctly add genre")
    @Test
    public void shouldCorrectAddAuthor() throws Exception {
        AuthorDto added = EXPECTED_AUTHORS.get(0);

        given(authorService.create(any(AuthorDto.class)))
                .willReturn(added);

        mvc.perform(post("/api/author")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .content(mapper.writeValueAsString(added))
        ).andExpect(status().isCreated())
         .andExpect(content().json(mapper.writeValueAsString(added)));

        verify(authorService, times(1))
                .create(any(AuthorDto.class));
    }

    @DisplayName("catch error validation create author")
//    @Test
    public void shouldCatchErrorValidationCreateAuthor() throws Exception {
        AuthorDto added = AuthorDto.builder()
                .firstName("12")
                .lastName("12")
                .age(17)
                .yearBirthdate(0)
                .build();

        mvc.perform(post("/api/author")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .content(mapper.writeValueAsString(added))
        ).andExpect(status().isBadRequest())
         .andExpect(content().string("Validation Error"));

        verify(authorService, times(0))
                .create(any(AuthorDto.class));
    }
}
