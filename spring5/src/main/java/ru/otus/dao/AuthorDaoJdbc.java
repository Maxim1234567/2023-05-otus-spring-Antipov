package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDaoJdbc extends DaoJdbc<Author> {
    List<Author> findAuthorByBookId(long bookId);
    void deleteAuthorByBookId(long bookId);
}
