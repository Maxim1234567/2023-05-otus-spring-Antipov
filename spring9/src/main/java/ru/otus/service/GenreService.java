package ru.otus.service;

import ru.otus.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> getAll();

    GenreDto create(GenreDto genre);

    GenreDto update(GenreDto genre);

    void delete(GenreDto genre);

    GenreDto getGenreById(Long id);
}