package ru.otus.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "list-book";
    }

    @GetMapping("/book")
    public String infoPage(@RequestParam("id") long id, Model model) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "info-book";
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDto book = bookService.getBookById(id);
        List<GenreDto> genres = genreService.getAll();
        List<AuthorDto> authors = authorService.getAll();

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return "edit-book";
    }

//    @PostMapping("/book/edit")
//    public String editPage(BookDto bookDto) {
//        bookService.update(bookDto);
//        return "redirect:/list-book";
//    }

    @PostMapping(value = "/book/edit", params = {"addGenre"})
    public String addGenre(@ModelAttribute("book") BookDto book,
                         BindingResult bindingResult, Model model) {
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();

        book.getGenres().forEach(g -> {
            String genre = genreService.getGenreById(g.getId()).getGenre();
            g.setGenre(genre);
        });

        book.getGenres().add(genres.get(0));

        book.getGenres().forEach(System.out::println);

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return "edit-book";
    }

    @PostMapping(value = "/book/edit", params = {"addAuthor"})
    public String addAuthor(@ModelAttribute("book") BookDto book,
                            BindingResult bindingResult, Model model) {
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();

        book.getAuthors().forEach(a -> {
            AuthorDto author = authorService.getAuthorById(a.getId());
            a.setFirstName(author.getFirstName());
            a.setLastName(author.getLastName());
        });

        book.getAuthors().add(authors.get(0));

        book.getAuthors().forEach(System.out::println);

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return "edit-book";
    }

    @PostMapping(value = "/book/edit", params = {"removeGenre"})
    public String removeGenre(@ModelAttribute("book") BookDto book,
                              BindingResult bindingResult, Model model, HttpServletRequest req) {
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();

        Long genreId = Long.valueOf(req.getParameter("removeGenre"));

        book.getGenres().removeIf(g -> g.getId().equals(genreId));

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return "edit-book";
    }

    @PostMapping(value = "/book/edit", params = {"removeAuthor"})
    public String removeAuthor(@ModelAttribute("book") BookDto book,
                               BindingResult bindingResult, Model model, HttpServletRequest req) {
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();

        Long authorId = Long.valueOf(req.getParameter("removeAuthor"));

        book.getAuthors().removeIf(a -> a.getId().equals(authorId));

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return "edit-book";
    }
}
