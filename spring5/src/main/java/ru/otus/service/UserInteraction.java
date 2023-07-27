package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

public interface UserInteraction {
    Genre createGenre();

    Author createAuthor();

    Book createBook();

    Long getId();
}
