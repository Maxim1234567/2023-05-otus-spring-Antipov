package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;

public interface BookService {
    Book getBookById(long id);

    List<Book> getAllBooks();

    Book save(Book book);

    void delete(Book book);
}