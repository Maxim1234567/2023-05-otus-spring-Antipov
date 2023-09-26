package ru.otus.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GenrePageController.class)
public class GenrePageControllerTest {
    @Autowired
    private MockMvc mvc;

    @DisplayName("should correctly return view list-genre")
    @Test
    @WithMockUser(username = "user")
    public void shouldReturnViewListGenre() throws Exception {
        mvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-genre"));
    }

    @DisplayName("should correctly return view create-genre")
    @Test
    @WithMockUser(username = "user")
    public void shouldReturnViewCreateGenre() throws Exception {
        mvc.perform(get("/genre/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-genre"));
    }
}