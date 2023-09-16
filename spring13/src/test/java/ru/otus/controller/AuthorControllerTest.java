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

    private static final String JSON_AUTHORS =
            "[\n" +
                    "    {\n" +
                    "        \"id\": 100,\n" +
                    "        \"firstName\": \"Herbert\",\n" +
                    "        \"lastName\": \"Shieldt\",\n" +
                    "        \"age\": 72,\n" +
                    "        \"yearBirthdate\": 1951,\n" +
                    "        \"name\": \"Herbert Shieldt\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": 200,\n" +
                    "        \"firstName\": \"Ivan\",\n" +
                    "        \"lastName\": \"Efremov\",\n" +
                    "        \"age\": 64,\n" +
                    "        \"yearBirthdate\": 1908,\n" +
                    "        \"name\": \"Ivan Efremov\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": 300,\n" +
                    "        \"firstName\": \"Isaac\",\n" +
                    "        \"lastName\": \"Asimov\",\n" +
                    "        \"age\": 72,\n" +
                    "        \"yearBirthdate\": 1919,\n" +
                    "        \"name\": \"Isaac Asimov\"\n" +
                    "    }\n" +
                    "]";

    private static final String JSON_AUTHOR =
            "{\n" +
                    "    \"id\": 100,\n" +
                    "    \"firstName\": \"Herbert\",\n" +
                    "    \"lastName\": \"Shieldt\",\n" +
                    "    \"age\": 72,\n" +
                    "    \"yearBirthdate\": 1951,\n" +
                    "    \"name\": \"Herbert Shieldt\"\n" +
                    "}";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @DisplayName("correctly return all author")
    @Test
    public void shouldCorrectReturnAllAuthor() throws Exception {
        List<AuthorDto> authors = List.of(
                new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951),
                new AuthorDto(200L, "Ivan", "Efremov", 64, 1908),
                new AuthorDto(300L, "Isaac", "Asimov", 72, 1919)
        );

        given(authorService.getAll())
                .willReturn(authors);

        mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_AUTHORS));

        verify(authorService, times(1))
                .getAll();
    }

    @DisplayName("correctly add genre")
    @Test
    public void shouldCorrectAddAuthor() throws Exception {
        AuthorDto added = new AuthorDto(100L, "Herbert", "Shieldt", 72, 1951);

        given(authorService.create(any(AuthorDto.class)))
                .willReturn(added);

        mvc.perform(post("/api/author")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsString(added))
                ).andExpect(status().isCreated())
                .andExpect(content().json(JSON_AUTHOR));

        verify(authorService, times(1))
                .create(any(AuthorDto.class));
    }
}