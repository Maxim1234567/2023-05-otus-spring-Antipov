package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.DomainConvert;
import ru.otus.service.IOService;
import ru.otus.service.ShowDomain;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShowDomainImpl implements ShowDomain {
    private final DomainConvert domainConvert;
    private final IOService ioService;

    @Override
    public void showBook(BookDto book) {
        ioService.println(domainConvert.convertBookToString(book));
    }

    @Override
    public void showListBook(List<BookDto> books) {
        books.forEach(this::showBook);
    }

    @Override
    public void showAuthor(AuthorDto author) {
        ioService.println(domainConvert.convertAuthorToString(author));
    }

    @Override
    public void showListAuthor(List<AuthorDto> authors) {
        authors.forEach(this::showAuthor);
    }

    @Override
    public void showGenre(GenreDto genre) {
        ioService.println(domainConvert.convertGenreToString(genre));
    }

    @Override
    public void showListGenre(List<GenreDto> genres) {
        genres.forEach(this::showGenre);
    }
}