package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.convert.BookConvertBookDto;
import ru.otus.convert.BookDtoConvertBook;
import ru.otus.domain.Author;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.exception.NotFoundException;
import ru.otus.exception.ValidationErrorException;
import ru.otus.repository.AuthorRepository;
import ru.otus.domain.Book;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final BookConvertBookDto convertBookDto;
    private final BookDtoConvertBook convertBook;

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);

        book.getGenres();
        book.getAuthors();
        book.getComments();

        return convertBookDto.convert(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(convertBookDto::convert).toList();
    }

    @Override
    @Transactional
    public BookDto save(BookDto book) {
        Book bookDomain = convertBook.convert(book);

        bookDomain.getAuthors().forEach(this::checkValidation);
        bookDomain.getGenres().forEach(this::checkValidation);

        List<Author> authors = authorRepository.findByIds(
                bookDomain.getAuthors().stream()
                        .map(Author::getId)
                        .filter(Objects::nonNull)
                        .toList()
        );

        if(authors.size() != bookDomain.getAuthors().size())
            throw new NotFoundException();

        List<Genre> genres = genreRepository.findByIds(
                bookDomain.getGenres().stream()
                        .map(Genre::getId)
                        .filter(Objects::nonNull)
                        .toList()
        );

        if(genres.size() != bookDomain.getGenres().size())
            throw new NotFoundException();

        bookDomain.setAuthors(authors);
        bookDomain.setGenres(genres);

        Book bookSave = bookRepository.save(bookDomain);

        return convertBookDto.convert(bookSave);
    }

    @Override
    @Transactional
    public void delete(BookDto book) {
        bookRepository.deleteById(book.getId());
    }

    private void checkValidation(Author author) {
        if(Objects.isNull(author.getId()))
            throw new ValidationErrorException("ID author is null");
    }

    private void checkValidation(Genre genre) {
        if(Objects.isNull(genre.getId()))
            throw new ValidationErrorException("ID genre is null");
    }
}