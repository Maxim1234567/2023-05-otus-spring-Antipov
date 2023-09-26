package ru.otus.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.is;

@WebMvcTest(BookPageController.class)
public class BookPageControllerTest {
    @Autowired
    private MockMvc mvc;

    @DisplayName("should return view list-book")
    @Test
    @WithMockUser(username = "user")
    public void shouldReturnViewListBook() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-book"));
    }

    @DisplayName("should return view create-book")
    @Test
    @WithMockUser(username = "user")
    public void shouldReturnViewCreateBook() throws Exception {
        mvc.perform(get("/book/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-book"));
    }

    @DisplayName("should return view update-book")
    @Test
    @WithMockUser(username = "user")
    public void shouldReturnViewUpdateBook() throws Exception {
        mvc.perform(get("/book/update")
                        .queryParam("id", "100")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("update-book"))
                .andExpect(model().attribute("id", is(100L)));
    }

    @DisplayName("should return view info-book")
    @Test
    @WithMockUser(username = "user")
    public void shouldReturnViewInfoBook() throws Exception {
        mvc.perform(get("/book/info")
                        .queryParam("id", "100")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("info-book"))
                .andExpect(model().attribute("id", is(100L)));
    }
}