package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

public interface ShowDomain {
    void showBook(Book book);

    void showListBook(List<Book> books);

    void showAuthor(Author author);

    void showListAuthor(List<Author> authors);

    void showGenre(Genre genre);

    void showListGenre(List<Genre> genres);
}