package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/book")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/api/book")
    public BookDto updateBook(@RequestBody BookDto book) {
        if(Objects.isNull(book.getComments()))
            book.setComments(List.of());

        book.getComments().forEach(c -> c.setBook(book));

        return bookService.update(book);
    }

    @PostMapping("/api/book")
    public BookDto createBook(@RequestBody BookDto book) {
        book.setComments(List.of());
        return bookService.create(book);
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        BookDto book = bookService.getBookById(id);
        bookService.delete(book);
    }
}
