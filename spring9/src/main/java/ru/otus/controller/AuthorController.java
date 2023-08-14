package ru.otus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.dto.AuthorDto;
import ru.otus.service.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/author")
    public String listPage(Model model) {
        List<AuthorDto> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "list-author";
    }

    @GetMapping("/author/create")
    public String createPage(Model model) {
        AuthorDto author = AuthorDto.builder().build();
        model.addAttribute("author", author);
        return "create-author";
    }

    @PostMapping("/author/create")
    public String createPage(@Valid @ModelAttribute("author") AuthorDto author,
                             BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "create-author";
        }

        authorService.create(author);
        return "redirect:/author";
    }
}
