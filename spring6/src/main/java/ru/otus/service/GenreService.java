package ru.otus.service;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    Genre save(Genre genre);

    void delete(Genre genre);

    Genre getGenreById(Long id);
}