package ru.otus.repository;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepositoryJdbc {
    Author save(Author author);

    Author findById(long id);

    List<Author> findByIds(List<Long> ids);

    List<Author> findByBookId(long bookId);

    List<Author> findByBookIds(List<Long> bookIds);

    List<Author> findAll();

    void deleteById(long id);
}