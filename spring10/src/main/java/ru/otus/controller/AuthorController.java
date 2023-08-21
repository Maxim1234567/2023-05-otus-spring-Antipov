package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.AuthorDto;
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
    public AuthorDto addAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.create(authorDto);
    }
}
