package ru.otus.repository;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findByIds(List<Long> ids);

    List<Book> findAll();

    void deleteById(long id);
}