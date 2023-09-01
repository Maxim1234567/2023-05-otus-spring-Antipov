package ru.otus.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenrePageController {
    @GetMapping("/genre")
    public String listGenre() {
        return "list-genre";
    }

    @GetMapping("/genre/create")
    public String createPage() {
        return "create-genre";
    }
}
