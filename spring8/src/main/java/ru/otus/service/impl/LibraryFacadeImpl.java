package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LibraryFacadeImpl implements LibraryFacade {
    private final IOService ioService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;
    private final ShowDomain showDomain;
    private final UserInteraction userInteraction;

    @Override
    public void createBook() {
        BookDto book = createBook("Create book!");
        bookService.create(book);
    }

    @Override
    public void updateBook() {
        ioService.print("Enter book id: ");
        String id = userInteraction.getId();
        BookDto book = createBook("Update book!");
        BookDto updateBook = new BookDto(
                null,
                book.getName(),
                book.getYearIssue(),
                book.getNumberPages(),
                book.getGenres(),
                book.getAuthors(),
                List.of()
        );
        bookService.create(updateBook);
    }

    private BookDto createBook(String text) {
        ioService.println(text);
        List<GenreDto> genres = new ArrayList<>();
        List<AuthorDto> authors = new ArrayList<>();

        showGenres();
        ioService.println("Choice genre. For exit enter -1!");
        String id;
        while (!Objects.equals(id = userInteraction.getId(), "-1")) {
            GenreDto genre = genreService.getGenreById(id);
            genres.add(genre);
        }

        showAuthors();
        ioService.println("Choice author. For exit enter -1!");
        while (!Objects.equals(id = userInteraction.getId(), "-1")) {
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
        authorService.create(author);
    }

    @Override
    public void createGenre() {
        ioService.println("Create genre!");
        GenreDto genre = userInteraction.createGenre();
        genreService.create(genre);
    }

    @Override
    public void createComment(String bookId) {
        ioService.println("Create comment!");
        CommentDto comment = userInteraction.createComment();
        comment.setBookId(bookId);
        commentService.create(comment);
    }

    @Override
    public void showBook() {
        String id = userInteraction.getId();
        BookDto book = bookService.getBookById(id);
        showDomain.showBook(book);
    }

    @Override
    public void showBooks() {
        ioService.println("Show all books");
        List<BookDto> books = bookService.getAllBooks();
        showDomain.showListBook(books);
    }

    @Override
    public void showAuthor() {
        String id = userInteraction.getId();
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
        String id = userInteraction.getId();
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
    public void showCommentsByBook(String bookId) {
        ioService.println("Show all comments by bookId " + bookId);
        List<CommentDto> comments = commentService.getAllCommentsByBookId(bookId);
        showDomain.showComments(comments);
    }

    @Override
    public void deleteBook() {
        String id = userInteraction.getId();
        BookDto book = bookService.getBookById(id);
        bookService.delete(book);
    }
}