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


@WebMvcTest(AuthorPageController.class)
public class AuthorPageControllerTest {
    @Autowired
    private MockMvc mvc;

    @DisplayName("should correctly return view list-author")
    @WithMockUser(
            username = "user"
    )
    @Test
    public void shouldReturnViewListAuthor() throws Exception {
        mvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-author"));
    }

    @DisplayName("should correctly return view create-author")
    @WithMockUser(
            username = "user"
    )
    @Test
    public void shouldReturnViewCreateAuthor() throws Exception {
        mvc.perform(get("/author/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-author"));
    }
}