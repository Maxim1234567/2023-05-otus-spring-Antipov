package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.convert.BookConvertBookDto;
import ru.otus.convert.BookDtoConvertBook;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.exception.AuthorNotFound;
import ru.otus.exception.GenreNotFound;
import ru.otus.repository.AuthorRepositoryJpa;
import ru.otus.repository.BookRepositoryJpa;
import ru.otus.domain.Book;
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
    private final GenreRepositoryJpa genreRepository;
    private final BookConvertBookDto convertBookDto;
    private final BookDtoConvertBook convertBook;

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(long id) {
        Book book = bookRepository.findById(id).orElse(new Book());

        book.getGenres();
        book.getAuthors();

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

        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();

        for(Author author: bookDomain.getAuthors()) {
            Author persistent;

            if(Objects.nonNull(author.getId())) {
                persistent = authorRepository.findById(author.getId())
                        .orElseThrow(AuthorNotFound::new);
            } else {
                persistent = author;
            }

            authors.add(persistent);
        }

        for(Genre genre: bookDomain.getGenres()) {
            Genre persistent;

            if(Objects.nonNull(genre.getId())) {
                persistent = genreRepository.findById(genre.getId())
                        .orElseThrow(GenreNotFound::new);
            } else {
                persistent = genre;
            }

            genres.add(persistent);
        }

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
}