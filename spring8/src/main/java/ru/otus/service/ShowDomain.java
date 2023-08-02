package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;

import java.util.List;

public interface ShowDomain {
    void showBook(BookDto book);

    void showListBook(List<BookDto> books);

    void showAuthor(AuthorDto author);

    void showListAuthor(List<AuthorDto> authors);

    void showGenre(GenreDto genre);

    void showListGenre(List<GenreDto> genres);

    void showComments(List<CommentDto> comments);
}