package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreRepositoryJdbc {
    Genre insert(Genre genre);

    Genre findById(long id);

    List<Genre> findByIds(List<Long> ids);

    List<Genre> findByBookId(long bookId);

    List<Genre> findByBookIds(List<Long> bookIds);

    List<Genre> findAll();

    void deleteById(long id);

    List<Genre> findAllUsed();
}