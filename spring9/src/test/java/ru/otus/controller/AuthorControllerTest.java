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
import ru.otus.service.AuthorService;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.Utils.assertEqualsAuthorDto;

@DisplayName("Controller to work with author should")
@WebMvcTest(AuthorController.class)
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

        mvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-author"))
                .andExpect(model().attribute("authors", hasSize(3)))
                .andExpect(model().attribute("authors", hasItem(
                        allOf(
                                hasProperty("id", is(author1.getId())),
                                hasProperty("age", is(author1.getAge())),
                                hasProperty("firstName", is(author1.getFirstName())),
                                hasProperty("lastName", is(author1.getLastName())),
                                hasProperty("yearBirthdate", is(author1.getYearBirthdate()))
                        )
                )))
                .andExpect(model().attribute("authors", hasItem(
                        allOf(
                                hasProperty("id", is(author2.getId())),
                                hasProperty("age", is(author2.getAge())),
                                hasProperty("firstName", is(author2.getFirstName())),
                                hasProperty("lastName", is(author2.getLastName())),
                                hasProperty("yearBirthdate", is(author2.getYearBirthdate()))
                        )
                )))
                .andExpect(model().attribute("authors", hasItem(
                        allOf(
                                hasProperty("id", is(author3.getId())),
                                hasProperty("age", is(author3.getAge())),
                                hasProperty("firstName", is(author3.getFirstName())),
                                hasProperty("lastName", is(author3.getLastName())),
                                hasProperty("yearBirthdate", is(author3.getYearBirthdate()))
                        )
                )));

        verify(authorService, times(1)).getAll();
    }

    @DisplayName("correctly create author")
    @Test
    public void shouldCorrectCreateAuthor() throws Exception {
        AuthorDto added = EXPECTED_AUTHORS.get(0);

        mvc.perform(post("/author/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", added.getId().toString())
                .param("age", Integer.toString(added.getAge()))
                .param("firstName",added.getFirstName() )
                .param("lastName", added.getLastName())
                .param("yearBirthdate", Integer.toString(added.getYearBirthdate()))
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/author"))
        .andExpect(redirectedUrl("/author"));

        ArgumentCaptor<AuthorDto> formObjectArgument = ArgumentCaptor.forClass(AuthorDto.class);
        verify(authorService, times(1)).create(formObjectArgument.capture());

        AuthorDto formObject = formObjectArgument.getValue();

        assertEqualsAuthorDto(formObject, added);
    }

    @DisplayName("catch error validation create author")
    @Test
    public void shouldCatchErrorValidationCreateAuthor() throws Exception {
        AuthorDto added = AuthorDto.builder()
                .firstName("Ma")
                .lastName("An")
                .age(17)
                .yearBirthdate(1995)
                .build();

        mvc.perform(post("/author/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", added.getFirstName())
                .param("lastName", added.getLastName())
                .param("age", Integer.toString(added.getAge()))
                .param("yearBirthdate", Integer.toString(added.getYearBirthdate()))
        )
        .andExpect(status().isOk())
        .andExpect(view().name("create-author"));

        verify(authorService, times(0)).create(ArgumentMatchers.any());
    }
}
