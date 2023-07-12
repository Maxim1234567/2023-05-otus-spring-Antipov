package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.repository.BookRepositoryJdbc;
import ru.otus.domain.Book;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepositoryJdbc bookRepository;

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return bookRepository.getBookById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    @Transactional
    public Book save(Book book) {
        Book newBook = null;

        if(Objects.isNull(book.getId())) {
            newBook = bookRepository.insert(book);
        } else {
            newBook = bookRepository.update(book);
        }

        return getBookById(newBook.getId());
    }

    @Override
    @Transactional
    public void delete(Book book) {
        bookRepository.deleteById(book.getId());
    }
}
