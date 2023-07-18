package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

public interface DomainConvert {
    String convertGenreToString(Genre genre);

    String convertAuthorToString(Author author);

    String convertBookToString(Book book);
}