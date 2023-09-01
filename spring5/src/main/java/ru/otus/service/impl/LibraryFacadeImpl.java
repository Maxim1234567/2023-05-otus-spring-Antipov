package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.AuthorService;
import ru.otus.service.IOService;
import ru.otus.service.LibraryFacade;
import ru.otus.service.GenreService;
import ru.otus.service.BookService;
import ru.otus.service.ShowDomain;
import ru.otus.service.UserInteraction;

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
        Book book = createBook("Create book!");
        bookService.save(book);
    }

    @Override
    public void updateBook() {
        ioService.print("Enter book id: ");
        Long id = userInteraction.getId();
        Book book = createBook("Update book!");
        Book updateBook = new Book(
                id,
                book.getName(),
                book.getYearIssue(),
                book.getNumberPages(),
                book.getGenres(),
                book.getAuthors()
        );
        bookService.save(updateBook);
    }

    private Book createBook(String text) {
        ioService.println(text);
        List<Genre> genres = new ArrayList<>();
        List<Author> authors = new ArrayList<>();

        showGenres();
        ioService.println("Choice genre. For exit enter -1!");
        Long id;
        while ((id = userInteraction.getId()) != -1) {
            Genre genre = genreService.getGenreById(id);
            genres.add(genre);
        }

        showAuthors();
        ioService.println("Choice author. For exit enter -1!");
        while ((id = userInteraction.getId()) != -1) {
            Author author = authorService.getAuthorById(id);
            authors.add(author);
        }

        Book book = userInteraction.createBook();
        book.setGenres(genres);
        book.setAuthors(authors);

        return book;
    }

    @Override
    public void createAuthor() {
        ioService.println("Create author!");
        Author author = userInteraction.createAuthor();
        authorService.save(author);
    }

    @Override
    public void createGenre() {
        ioService.println("Create genre!");
        Genre genre = userInteraction.createGenre();
        genreService.save(genre);
    }

    @Override
    public void showBook() {
        Long id = userInteraction.getId();
        Book book = bookService.getBookById(id);
        showDomain.showBook(book);
    }

    @Override
    public void showBooks() {
        ioService.println("Show all books");
        List<Book> books = bookService.getAllBooks();
        showDomain.showListBook(books);
    }

    @Override
    public void showAuthor() {
        Long id = userInteraction.getId();
        Author author = authorService.getAuthorById(id);
        showDomain.showAuthor(author);
    }

    @Override
    public void showAuthors() {
        ioService.println("Show all authors");
        List<Author> authors = authorService.getAll();
        showDomain.showListAuthor(authors);
    }

    @Override
    public void showGenre() {
        Long id = userInteraction.getId();
        Genre genre = genreService.getGenreById(id);
        showDomain.showGenre(genre);
    }

    @Override
    public void showGenres() {
        ioService.println("Show all genres");
        List<Genre> genres = genreService.getAll();
        showDomain.showListGenre(genres);
    }

    @Override
    public void deleteBook() {
        Long id = userInteraction.getId();
        Book book = bookService.getBookById(id);
        bookService.delete(book);
    }
}
