package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.DomainConvert;

@Service
public class DomainConvertImpl implements DomainConvert {
    @Override
    public String convertGenreToString(GenreDto genre) {
        return genre.getId() + ". | " + genre.getGenre();
    }

    @Override
    public String convertAuthorToString(AuthorDto author) {
        return author.getId() + ". | " + author.getFirstName() + " " + author.getLastName() + " | " + author.getYearBirthdate() + " | " + author.getAge();
    }

    @Override
    public String convertCommentToString(CommentDto comment) {
        return comment.getId() + ". | " + comment.getComments();
    }

    @Override
    public String convertBookToString(BookDto book) {
        String bookString = book.getId() + ". | " + book.getName() + " | " + book.getYearIssue() + " | " + book.getNumberPages();

        StringBuilder bookBuilder = new StringBuilder();
        StringBuilder authors = new StringBuilder();
        StringBuilder genres = new StringBuilder();
        StringBuilder comments = new StringBuilder();

        book.getAuthors().forEach(a -> authors.append("  " + convertAuthorToString(a)));

        book.getGenres().forEach(g -> genres.append("  " + convertGenreToString(g)));

        book.getComments().forEach(c -> comments.append("  " + convertCommentToString(c)));

        bookBuilder.append(bookString + "\n");
        if (!book.getAuthors().isEmpty()) {
            bookBuilder.append("----------------------------------------\n");
            bookBuilder.append(authors + "\n");
        }
        if (!book.getGenres().isEmpty()) {
            bookBuilder.append("----------------------------------------\n");
            bookBuilder.append(genres);
        }
        if (!book.getComments().isEmpty()) {
            bookBuilder.append("----------------------------------------\n");
            bookBuilder.append(comments);
        }
        bookBuilder.append("\n");

        return bookBuilder.toString();
    }
}