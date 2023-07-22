package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LibraryFacadeImpl implements LibraryFacade {
    private final IOService ioService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final ShowDomain showDomain;
    private final UserInteraction userInteraction;

    @Override
    public void createBook() {
        BookDto book = createBook("Create book!");
        bookService.save(book);
    }

    @Override
    public void updateBook() {
        ioService.print("Enter book id: ");
        Long id = userInteraction.getId();
        BookDto book = createBook("Update book!");
        BookDto updateBook = new BookDto(
                id,
                book.getName(),
                book.getYearIssue(),
                book.getNumberPages(),
                book.getGenres(),
                book.getAuthors()
        );
        bookService.save(updateBook);
    }

    private BookDto createBook(String text) {
        ioService.println(text);
        List<GenreDto> genres = new ArrayList<>();
        List<AuthorDto> authors = new ArrayList<>();

        showGenres();
        ioService.println("Choice genre. For exit enter -1!");
        Long id;
        while ((id = userInteraction.getId()) != -1) {
            GenreDto genre = genreService.getGenreById(id);
            genres.add(genre);
        }

        showAuthors();
        ioService.println("Choice author. For exit enter -1!");
        while ((id = userInteraction.getId()) != -1) {
            AuthorDto author = authorService.getAuthorById(id);
            authors.add(author);
        }

        BookDto book = userInteraction.createBook();
        book.setGenres(genres);
        book.setAuthors(authors);

        return book;
    }

    @Override
    public void createAuthor() {
        ioService.println("Create author!");
        AuthorDto author = userInteraction.createAuthor();
        authorService.save(author);
    }

    @Override
    public void createGenre() {
        ioService.println("Create genre!");
        GenreDto genre = userInteraction.createGenre();
        genreService.save(genre);
    }

    @Override
    public void showBook() {
        Long id = userInteraction.getId();
//        Book book = bookService.getBookById(id);
//        showDomain.showBook(book);
    }

    @Override
    public void showBooks() {
        ioService.println("Show all books");
        List<BookDto> books = bookService.getAllBooks();
        showDomain.showListBook(books);
    }

    @Override
    public void showAuthor() {
        Long id = userInteraction.getId();
        AuthorDto author = authorService.getAuthorById(id);
        showDomain.showAuthor(author);
    }

    @Override
    public void showAuthors() {
        ioService.println("Show all authors");
        List<AuthorDto> authors = authorService.getAll();
        showDomain.showListAuthor(authors);
    }

    @Override
    public void showGenre() {
        Long id = userInteraction.getId();
        GenreDto genre = genreService.getGenreById(id);
        showDomain.showGenre(genre);
    }

    @Override
    public void showGenres() {
        ioService.println("Show all genres");
        List<GenreDto> genres = genreService.getAll();
        showDomain.showListGenre(genres);
    }

    @Override
    public void deleteBook() {
        Long id = userInteraction.getId();
//        Book book = bookService.getBookById(id);
//        bookService.delete(book);
    }
}