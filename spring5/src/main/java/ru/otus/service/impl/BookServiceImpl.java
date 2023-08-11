package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.exception.AuthorNotFound;
import ru.otus.exception.BookNotFound;
import ru.otus.exception.GenreNotFound;
import ru.otus.repository.AuthorRepositoryJdbc;
import ru.otus.repository.BookRepositoryJdbc;
import ru.otus.domain.Book;
import ru.otus.repository.GenreRepositoryJdbc;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepositoryJdbc bookRepository;

    private final GenreRepositoryJdbc genreRepository;

    private final AuthorRepositoryJdbc authorRepository;

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    @Transactional
    public Book save(Book book) {
        Book newBook;

        existsGenre(book.getGenres().stream().map(Genre::getId).toList());
        existsAuthor(book.getAuthors().stream().map(Author::getId).toList());

        if(Objects.isNull(book.getId())) {
            newBook = bookRepository.insert(book);
        } else {
            existsBook(book.getId());
            newBook = bookRepository.update(book);
        }

        return getBookById(newBook.getId());
    }

    @Override
    @Transactional
    public void delete(Book book) {
        bookRepository.deleteById(book.getId());
    }

    private void existsGenre(List<Long> ids) {
        List<Genre> genres = genreRepository.findByIds(ids);

        if(ids.size() != genres.size())
            throw new GenreNotFound();
    }

    private void existsAuthor(List<Long> ids) {
        List<Author> authors = authorRepository.findByIds(ids);
        if(ids.size() != authors.size())
            throw new AuthorNotFound();
    }

    private void existsBook(long bookId) {
        try {
            bookRepository.findById(bookId);
        } catch (EmptyResultDataAccessException e) {
            throw new BookNotFound(e);
        }
    }
}
