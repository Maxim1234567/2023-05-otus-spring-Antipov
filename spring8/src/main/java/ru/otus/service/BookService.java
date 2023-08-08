package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto getBookById(String id);

    List<BookDto> getAllBooks();

    BookDto save(BookDto book);

    void delete(BookDto book);
}