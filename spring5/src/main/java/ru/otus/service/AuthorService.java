package ru.otus.service;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author save(Author author);

    void delete(Author author);

    Author getAuthorById(Long id);
}
