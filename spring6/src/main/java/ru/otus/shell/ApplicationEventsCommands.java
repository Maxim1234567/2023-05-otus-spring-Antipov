package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.LibraryFacade;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {
    private final LibraryFacade libraryFacade;

    @ShellMethod(value = "Show all genres", key = {"gs", "genres"})
    public String showAllGenre() {
        libraryFacade.showGenres();
        return "";
    }

    @ShellMethod(value = "Show genre", key = {"g", "genre"})
    public String showGenre() {
        libraryFacade.showGenre();
        return "";
    }

    @ShellMethod(value = "Show all authors", key = {"as", "authors"})
    public String showAllAuthors() {
        libraryFacade.showAuthors();
        return "";
    }

    @ShellMethod(value = "Show author", key = {"a", "author"})
    public String showAuthor() {
        libraryFacade.showAuthor();
        return "";
    }

    @ShellMethod(value = "Show all books", key = {"bs", "books"})
    public String showAllBooks() {
        libraryFacade.showBooks();
        return "";
    }

    @ShellMethod(value = "Show book", key = {"b", "book"})
    public String showBook() {
        libraryFacade.showBook();
        return "";
    }

    @ShellMethod(value = "Show book comments", key = {"comm", "comments"})
    public String showComments(String stringBookId) {
        long bookId = Long.parseLong(stringBookId);
        libraryFacade.showCommentsByBook(bookId);
        return "";
    }

    @ShellMethod(value = "Delete book", key = {"d", "delete"})
    public String deleteBook() {
        libraryFacade.deleteBook();
        return "";
    }

    @ShellMethod(value = "Create genre", key = {"cg", "create-genre"})
    public String createGenre() {
        libraryFacade.createGenre();
        return "";
    }

    @ShellMethod(value = "Create author", key = {"ca", "create-author"})
    public String createAuthor() {
        libraryFacade.createAuthor();
        return "";
    }

    @ShellMethod(value = "Create book", key = {"cb", "create-book"})
    public String createBook() {
        libraryFacade.createBook();
        return "";
    }

    @ShellMethod(value = "Create comments", key = {"cc", "create-comment"})
    public String createComment(String stringBookId) {
        long bookId = Long.parseLong(stringBookId);
        libraryFacade.createComment(bookId);
        return "";
    }
}