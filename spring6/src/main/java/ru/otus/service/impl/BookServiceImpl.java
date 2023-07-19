package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.convert.BookConvertBookDto;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.repository.BookRepositoryJdbc;
import ru.otus.domain.Book;
import ru.otus.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepositoryJdbc bookRepository;
    private final BookConvertBookDto bookConvert;

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(long id) {
        Book book = bookRepository.findById(id);

        List<Genre> genres = book.getGenres();
        List<Author> authors = book.getAuthors();

        book.setGenres(genres);
        book.setAuthors(authors);

        return bookConvert.convert(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    @Transactional
    public BookDto save(Book book) {
        Book newBook;

        newBook = bookRepository.save(book);

        return getBookById(newBook.getId());
    }

    @Override
    @Transactional
    public void delete(Book book) {
        bookRepository.deleteById(book.getId());
    }
}