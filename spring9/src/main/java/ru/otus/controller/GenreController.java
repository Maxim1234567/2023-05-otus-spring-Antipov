package ru.otus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.dto.GenreDto;
import ru.otus.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genre")
    public String listPage(Model model) {
        List<GenreDto> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "list-genre";
    }

    @GetMapping("/genre/create")
    public String createPage(Model model) {
        GenreDto genre = GenreDto.builder().build();
        model.addAttribute("genre", genre);
        return "create-genre";
    }

    @PostMapping("/genre/create")
    public String createPage(@Valid @ModelAttribute("genre") GenreDto genre,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create-genre";
        }

        genreService.create(genre);
        return "redirect:/genre";
    }
}
