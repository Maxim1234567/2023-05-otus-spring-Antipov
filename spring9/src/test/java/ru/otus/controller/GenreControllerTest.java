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
import ru.otus.dto.GenreDto;
import ru.otus.service.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.Utils.assertEqualsGenreDto;

@DisplayName("Controller to work with genre should ")
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

        mvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-genre"))
                .andExpect(model().attribute("genres", hasSize(3)))
                .andExpect(model().attribute("genres", hasItem(
                        allOf(
                                hasProperty("id", is(genre1.getId())),
                                hasProperty("genre", is(genre1.getGenre()))
                        )
                )))
                .andExpect(model().attribute("genres", hasItem(
                        allOf(
                                hasProperty("id", is(genre2.getId())),
                                hasProperty("genre", is(genre2.getGenre()))
                        )
                )))
                .andExpect(model().attribute("genres", hasItem(
                        allOf(
                                hasProperty("id", is(genre3.getId())),
                                hasProperty("genre", is(genre3.getGenre()))
                        )
                )));

        verify(genreService, times(1)).getAll();
    }

    @DisplayName("correctly create genre")
    @Test
    public void shouldCorrectCreateGenre() throws Exception {
        GenreDto added = EXPECTED_GENRES.get(0);

        mvc.perform(post("/genre/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", added.getId().toString())
                        .param("genre", added.getGenre())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/genre"))
                .andExpect(redirectedUrl("/genre"));

        ArgumentCaptor<GenreDto> formObjectArgument = ArgumentCaptor.forClass(GenreDto.class);
        verify(genreService, times(1)).create(formObjectArgument.capture());

        GenreDto formObject = formObjectArgument.getValue();

        assertEqualsGenreDto(formObject, added);
    }

    @DisplayName("catch error validation crate genre")
    @Test
    public void shouldCatchErrorValidationCreateGenre() throws Exception {
        GenreDto added = GenreDto.builder()
                .genre("12")
                .build();

        mvc.perform(post("/genre/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("genre", added.getGenre())
        )
        .andExpect(status().isOk())
        .andExpect(view().name("create-genre"));

        verify(genreService, times(0)).create(ArgumentMatchers.any());
    }
}
