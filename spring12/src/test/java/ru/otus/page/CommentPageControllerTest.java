package ru.otus.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CommentPageController.class)
public class CommentPageControllerTest {
    @Autowired
    private MockMvc mvc;

    @DisplayName("correctly return view create-comment")
    @WithMockUser(
            username = "user",
            authorities = "USER"
    )
    @Test
    public void shouldReturnViewCreateComment() throws Exception {
        mvc.perform(get("/comment/create")
                        .queryParam("id", "100")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("create-comment"))
                .andExpect(model().attribute("id", is(100L)));
    }
}
