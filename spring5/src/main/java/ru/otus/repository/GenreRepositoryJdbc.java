package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreRepositoryJdbc {
    Genre insert(Genre genre);

    Genre getGenreById(long id);

    List<Genre> findAllGenresByIds(List<Long> ids);

    List<Genre> getAllGenres();

    void deleteById(long id);

    List<Genre> findAllUsed();
}
