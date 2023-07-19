package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto getBookById(long id);

    List<Book> getAllBooks();

    BookDto save(Book book);

    void delete(Book book);
}