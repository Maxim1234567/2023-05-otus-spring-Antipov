package ru.otus.service;

import ru.otus.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto getBookById(String id);

    List<BookDto> getAllBooks();

    BookDto create(BookDto book);

    BookDto update(BookDto book);

    void delete(BookDto book);
}