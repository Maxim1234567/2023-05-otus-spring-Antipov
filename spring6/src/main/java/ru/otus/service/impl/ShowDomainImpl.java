package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
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
    public void showBook(Book book) {
        ioService.println(domainConvert.convertBookToString(book));
    }

    @Override
    public void showListBook(List<Book> books) {
        books.forEach(this::showBook);
    }

    @Override
    public void showAuthor(Author author) {
        ioService.println(domainConvert.convertAuthorToString(author));
    }

    @Override
    public void showListAuthor(List<Author> authors) {
        authors.forEach(this::showAuthor);
    }

    @Override
    public void showGenre(Genre genre) {
        ioService.println(domainConvert.convertGenreToString(genre));
    }

    @Override
    public void showListGenre(List<Genre> genres) {
        genres.forEach(this::showGenre);
    }
}