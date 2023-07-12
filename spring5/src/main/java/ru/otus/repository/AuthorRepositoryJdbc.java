package ru.otus.repository;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepositoryJdbc {
    Author insert(Author author);

    Author getAuthorById(long id);

    List<Author> findAllAuthorsByIds(List<Long> ids);

    List<Author> getAllAuthors();

    void deleteById(long id);

    List<Author> findAllUsed();
}
