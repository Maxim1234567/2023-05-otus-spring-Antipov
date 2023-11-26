package ru.otus.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookPageController {
    @GetMapping("/")
    public String listPage() {
        return "list-book";
    }

    @GetMapping("/book/create")
    public String createPage() {
        return "create-book";
    }

    @GetMapping("/book/update")
    public String updatePage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return "update-book";
    }

    @GetMapping("/book/info")
    public String infoPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return "info-book";
    }
}