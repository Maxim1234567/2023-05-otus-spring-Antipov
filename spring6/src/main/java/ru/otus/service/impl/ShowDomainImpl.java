package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
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
    public void showBooks(List<BookDto> books) {
        books.forEach(this::showBook);
    }

    @Override
    public void showAuthor(AuthorDto author) {
        ioService.println(domainConvert.convertAuthorToString(author));
    }

    @Override
    public void showAuthors(List<AuthorDto> authors) {
        authors.forEach(this::showAuthor);
    }

    @Override
    public void showGenre(GenreDto genre) {
        ioService.println(domainConvert.convertGenreToString(genre));
    }

    @Override
    public void showGenres(List<GenreDto> genres) {
        genres.forEach(this::showGenre);
    }

    @Override
    public void showComments(List<CommentDto> comments) {
        comments.forEach(
                c -> ioService.println(domainConvert.convertCommentToString(c))
        );
    }
}