package ru.otus.repository;

import ru.otus.domain.Book;

import java.util.List;

public interface BookRepositoryJdbc {
    Book save(Book book);

    Book findById(long id);

    List<Book> findByIds(List<Long> ids);

    List<Book> getAllBooks();

    void deleteById(long id);
}