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
import ru.otus.repository.AuthorRepositoryJpa;
import ru.otus.repository.BookRepositoryJpa;
import ru.otus.domain.Book;
import ru.otus.repository.CommentRepositoryJpa;
import ru.otus.repository.GenreRepositoryJpa;
import ru.otus.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepositoryJpa bookRepository;
    private final AuthorRepositoryJpa authorRepository;
    private final CommentRepositoryJpa commentRepository;
    private final GenreRepositoryJpa genreRepository;
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

        List<Author> authors = authorRepository.findByIds(
                bookDomain.getAuthors().stream()
                        .map(Author::getId)
                        .filter(Objects::nonNull)
                        .toList()
        );
        authors.addAll(
                bookDomain.getAuthors().stream()
                        .filter(a -> Objects.isNull(a.getId()))
                        .toList()
        );

        List<Genre> genres = genreRepository.findByIds(
                bookDomain.getGenres().stream()
                        .map(Genre::getId)
                        .filter(Objects::nonNull)
                        .toList()
        );
        genres.addAll(
                bookDomain.getGenres().stream()
                        .filter(g -> Objects.isNull(g.getId()))
                        .toList()
        );

        List<Comment> comments = commentRepository.findByIds(
                bookDomain.getComments().stream()
                        .map(Comment::getId)
                        .filter(Objects::nonNull)
                        .toList()
        );
        comments.addAll(
                bookDomain.getComments().stream()
                        .filter(c -> Objects.isNull(c.getId()))
                        .toList()
        );

        bookDomain.setAuthors(authors);
        bookDomain.setGenres(genres);
        bookDomain.setComments(comments);

        Book bookSave = bookRepository.save(bookDomain);

        return convertBookDto.convert(bookSave);
    }

    @Override
    @Transactional
    public void delete(BookDto book) {
        bookRepository.deleteById(book.getId());
    }
}