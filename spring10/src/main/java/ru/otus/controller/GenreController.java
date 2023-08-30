package ru.otus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.GenreDto;
import ru.otus.exception.ValidationErrorException;
import ru.otus.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/api/genre")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @PostMapping("/api/genre")
    public ResponseEntity<GenreDto> addGenre(@Valid @RequestBody GenreDto genre) {
        return ResponseEntity.ok(genreService.create(genre));
    }
}
