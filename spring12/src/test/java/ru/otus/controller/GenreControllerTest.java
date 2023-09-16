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
import ru.otus.dto.GenreDto;
import ru.otus.service.GenreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Controller to work with genre should")
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    private static final String JSON_GENRES =
            "[\n" +
            "    {\n" +
            "        \"id\": 100,\n" +
            "        \"genre\": \"Fiction\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 200,\n" +
            "        \"genre\": \"Novel\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 300,\n" +
            "        \"genre\": \"Thriller\"\n" +
            "    }\n" +
            "]";

    private static final String JSON_GENRE =
            "{\n" +
            "    \"id\": 100,\n" +
            "    \"genre\": \"Fiction\"\n" +
            "}";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    @DisplayName("correctly return all genre")
    @WithMockUser(
            username = "user",
            authorities = "USER"
    )
    @Test
    public void shouldCorrectReturnAllGenre() throws Exception {
        List<GenreDto> genres = List.of(
                new GenreDto(100L, "Fiction"),
                new GenreDto(200L, "Novel"),
                new GenreDto(300L, "Thriller")
        );

        given(genreService.getAll())
                .willReturn(genres);

        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_GENRES));

        verify(genreService, times(1))
                .getAll();
    }

    @DisplayName("correctly add genre")
    @WithMockUser(
            username = "user",
            authorities = "USER"
    )
    @Test
    public void shouldCorrectAddGenre() throws Exception {
        GenreDto added = new GenreDto(100L, "Fiction");

        given(genreService.create(any(GenreDto.class)))
                .willReturn(added);

        mvc.perform(post("/api/genre")
                        .with(csrf())
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(added))
                ).andExpect(status().isOk())
                .andExpect(content().json(JSON_GENRE));

        verify(genreService, times(1))
                .create(any(GenreDto.class));
    }
}
