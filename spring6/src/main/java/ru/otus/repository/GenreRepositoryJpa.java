package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepositoryJpa {
    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findByIds(List<Long> ids);

    List<Genre> findByBookId(long bookId);

    List<Genre> findByBookIds(List<Long> bookIds);

    List<Genre> findAll();

    void deleteById(long id);
}