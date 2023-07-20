package ru.otus.service;

import ru.otus.domain.Genre;
import ru.otus.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> getAll();

    GenreDto save(GenreDto genre);

    void delete(GenreDto genre);

    GenreDto getGenreById(Long id);
}