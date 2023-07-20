package ru.otus.repository;

import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa {
    Author save(Author author);

    Optional<Author> findById(long id);

    List<Author> findByIds(List<Long> ids);

    List<Author> findByBookId(long bookId);

    List<Author> findByBookIds(List<Long> bookIds);

    List<Author> findAll();

    void deleteById(long id);
}