package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.LibraryService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {
    private final LibraryService libraryService;

    @ShellMethod(value = "Show all genres", key = {"gs", "genres"})
    public String showAllGenre() {
        libraryService.showGenres();
        return "";
    }

    @ShellMethod(value = "Show genre", key = {"g", "genre"})
    public String showGenre() {
        libraryService.showGenre();
        return "";
    }

    @ShellMethod(value = "Show all authors", key = {"as", "authors"})
    public String showAllAuthors() {
        libraryService.showAuthors();
        return "";
    }

    @ShellMethod(value = "Show author", key = {"a", "author"})
    public String showAuthor() {
        libraryService.showAuthor();
        return "";
    }

    @ShellMethod(value = "Show all books", key = {"bs", "books"})
    public String showAllBooks() {
        libraryService.showBooks();
        return "";
    }

    @ShellMethod(value = "Show book", key = {"b", "book"})
    public String showBook() {
        libraryService.showBook();
        return "";
    }

    @ShellMethod(value = "Delete book", key = {"d", "delete"})
    public String deleteBook() {
        libraryService.deleteBook();
        return "";
    }

    @ShellMethod(value = "Create genre", key = {"cg", "create-genre"})
    public String createGenre() {
        libraryService.createGenre();
        return "";
    }

    @ShellMethod(value = "Create author", key = {"ca", "create-author"})
    public String createAuthor() {
        libraryService.createAuthor();
        return "";
    }

    @ShellMethod(value = "Create book", key = {"cb", "create-book"})
    public String createBook() {
        libraryService.createBook();
        return "";
    }
}
