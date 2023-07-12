package ru.otus.repository;

import ru.otus.domain.Book;

import java.util.List;

public interface BookRepositoryJdbc {
    Book insert(Book book);

    Book update(Book book);

    Book getBookById(long id);

    List<Book> findAllBooksById(List<Long> ids);

    List<Book> getAllBooks();

    void deleteById(long id);
}
