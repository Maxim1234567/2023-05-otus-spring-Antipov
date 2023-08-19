package ru.otus.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @PostMapping("/book/edit")
    public String editPage(@Valid @ModelAttribute("book") BookDto bookDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-book";
        }

        if (Objects.nonNull(bookDto.getComments())) {
            bookDto.getComments().forEach(c -> c.setBook(bookDto));
        } else {
            bookDto.setComments(List.of());
        }

        bookService.update(bookDto);
        return "redirect:/";
    }

    @GetMapping("/book/create")
    public String createPage(Model model) {
        BookDto book = BookDto.builder()
                .genres(new ArrayList<>())
                .authors(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();

        model.addAttribute("book", book);

        return "create-book";
    }

    @PostMapping("/book/create")
    public String createPage(@Valid @ModelAttribute("book") BookDto book,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create-book";
        }

        book.setComments(List.of());
        bookService.create(book);
        return "redirect:/";
    }

    @PostMapping("/book/delete")
    public String deletePage(@RequestParam("id") long id) {
        BookDto book = bookService.getBookById(id);
        bookService.delete(book);
        return "redirect:/";
    }

    @PostMapping(value = {"/book/edit", "/book/create"}, params = {"addGenre"})
    public String addGenre(@ModelAttribute("book") BookDto book, Model model) {
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();

        if (Objects.isNull(book.getGenres())) {
            book.setGenres(new ArrayList<>());
        }

        book.getGenres().forEach(g -> {
            String genre = genreService.getGenreById(g.getId()).getGenre();
            g.setGenre(genre);
        });

        book.getGenres().add(genres.get(0));

        book.getGenres().forEach(System.out::println);

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return Objects.isNull(book.getId()) ? "create-book" : "edit-book";
    }

    @PostMapping(value = {"/book/edit", "/book/create"}, params = {"addAuthor"})
    public String addAuthor(@ModelAttribute("book") BookDto book, Model model) {
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();

        if (Objects.isNull(book.getAuthors())) {
            book.setAuthors(new ArrayList<>());
        }

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

        return Objects.isNull(book.getId()) ? "create-book" : "edit-book";
    }

    @PostMapping(value = {"/book/edit", "/book/create"}, params = {"removeGenre"})
    public String removeGenre(@ModelAttribute("book") BookDto book,
                              Model model, HttpServletRequest req) {
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();

        Long genreId = Long.valueOf(req.getParameter("removeGenre"));

        if (Objects.isNull(book.getGenres())) {
            book.setGenres(new ArrayList<>());
        }

        book.getGenres().removeIf(g -> g.getId().equals(genreId));

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return Objects.isNull(book.getId()) ? "create-book" : "edit-book";
    }

    @PostMapping(value = {"/book/edit", "/book/create"}, params = {"removeAuthor"})
    public String removeAuthor(@ModelAttribute("book") BookDto book,
                               Model model, HttpServletRequest req) {
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();

        Long authorId = Long.valueOf(req.getParameter("removeAuthor"));

        if (Objects.isNull(book.getAuthors())) {
            book.setAuthors(new ArrayList<>());
        }

        book.getAuthors().removeIf(a -> a.getId().equals(authorId));

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return Objects.isNull(book.getId()) ? "create-book" : "edit-book";
    }
}
