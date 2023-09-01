package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.dto.GenreDto;
import ru.otus.service.GenreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Controller to work with genre should")
@WebMvcTest(GenreController.class)
public class GenreControllerTest {
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

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    @DisplayName("correctly return all genre")
    @Test
    public void shouldCorrectReturnAllGenre() throws Exception {
        GenreDto genre1 = EXPECTED_GENRES.get(0);
        GenreDto genre2 = EXPECTED_GENRES.get(1);
        GenreDto genre3 = EXPECTED_GENRES.get(2);

        List<GenreDto> genres = List.of(
                genre1, genre2, genre3
        );

        given(genreService.getAll())
                .willReturn(genres);

        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres)));

        verify(genreService, times(1))
                .getAll();
    }

    @DisplayName("correctly add genre")
    @Test
    public void shouldCorrectAddGenre() throws Exception {
        GenreDto added = EXPECTED_GENRES.get(0);

        given(genreService.create(any(GenreDto.class)))
                .willReturn(added);

        mvc.perform(post("/api/genre")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(added))
                ).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(added)));

        verify(genreService, times(1))
                .create(any(GenreDto.class));
    }

    @DisplayName("catch error validation create genre")
//    @Test
    public void shouldCatchErrorValidationCreateGenre() throws Exception {
        GenreDto added = GenreDto.builder()
                .genre("12")
                .build();

        mvc.perform(post("/api/genre")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(added))
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("Validation Error"));

        verify(genreService, times(0))
                .create(any(GenreDto.class));
    }
}
