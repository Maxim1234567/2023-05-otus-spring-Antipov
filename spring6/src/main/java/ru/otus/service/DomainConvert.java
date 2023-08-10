package ru.otus.service;

import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;

public interface DomainConvert {
    String convertGenreToString(GenreDto genre);

    String convertAuthorToString(AuthorDto author);

    String convertCommentToString(CommentDto comment);

    String convertBookToString(BookDto book);
}