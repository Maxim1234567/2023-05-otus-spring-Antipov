package ru.otus.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentPageController {
    @GetMapping("/comment/create")
    public String createPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return "create-comment";
    }
}