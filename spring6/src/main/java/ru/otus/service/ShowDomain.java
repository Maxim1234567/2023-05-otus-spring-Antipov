package ru.otus.service;

import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;

import java.util.List;

public interface ShowDomain {
    void showBook(BookDto book);

    void showBooks(List<BookDto> books);

    void showAuthor(AuthorDto author);

    void showAuthors(List<AuthorDto> authors);

    void showGenre(GenreDto genre);

    void showGenres(List<GenreDto> genres);

    void showComments(List<CommentDto> comments);
}