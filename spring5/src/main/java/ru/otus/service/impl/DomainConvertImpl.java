package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.DomainConvert;

@Service
public class DomainConvertImpl implements DomainConvert {
    @Override
    public String convertGenreToString(Genre genre) {
        return genre.getId() + ". | " + genre.getGenre();
    }

    @Override
    public String convertAuthorToString(Author author) {
        return author.getId() + ". | " + author.getFirstName() + " " + author.getLastName() + " | " + author.getYearBirthdate() + " | " + author.getAge();
    }

    @Override
    public String convertBookToString(Book book) {
        String bookString = book.getId() + ". | " + book.getName() + " | " + book.getYearIssue() + " | " + book.getNumberPages();

        StringBuilder bookBuilder = new StringBuilder();
        StringBuilder authors = new StringBuilder();
        StringBuilder genres = new StringBuilder();

        book.getAuthors().forEach(a -> authors.append("  " + convertAuthorToString(a)));

        book.getGenres().forEach(g -> genres.append("  " + convertGenreToString(g)));

        bookBuilder.append(bookString + "\n");
        if (!book.getAuthors().isEmpty()) {
            bookBuilder.append("----------------------------------------\n");
            bookBuilder.append(authors + "\n");
        }
        if (!book.getGenres().isEmpty()) {
            bookBuilder.append("----------------------------------------\n");
            bookBuilder.append(genres);
        }
        bookBuilder.append("\n");

        return bookBuilder.toString();
    }
}
