package ru.otus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.AuthorDto;
import ru.otus.exception.ValidationErrorException;
import ru.otus.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/api/author")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAll();
    }

    @PostMapping("/api/author")
    public AuthorDto addAuthor(@Valid @RequestBody AuthorDto authorDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException("Validation Error");
        }

        return authorService.create(authorDto);
    }
}
