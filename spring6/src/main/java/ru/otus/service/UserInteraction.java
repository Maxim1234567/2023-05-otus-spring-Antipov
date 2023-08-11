package ru.otus.service;

import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;

public interface UserInteraction {
    GenreDto createGenre();

    AuthorDto createAuthor();

    BookDto createBook();

    CommentDto createComment();

    Long getId();
}