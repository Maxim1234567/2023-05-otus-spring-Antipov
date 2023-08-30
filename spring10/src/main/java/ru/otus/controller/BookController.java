package ru.otus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/book")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/api/book/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/api/book/{id}")
    public ResponseEntity<BookDto> updateBook(@Valid @RequestBody BookDto book, @PathVariable("id") String id) {
        return ResponseEntity.ok(bookService.update(book));
    }

    @PostMapping("/api/book")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(book));
    }

    @DeleteMapping("/api/book/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") long id) {
        BookDto book = bookService.getBookById(id);
        bookService.delete(book);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
