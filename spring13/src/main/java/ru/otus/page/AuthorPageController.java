package ru.otus.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorPageController {
    @GetMapping("/author")
    public String listPage() {
        return "list-author";
    }

    @GetMapping("/author/create")
    public String createPage() {
        return "create-author";
    }
}