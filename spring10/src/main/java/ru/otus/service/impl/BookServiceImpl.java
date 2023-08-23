package ru.otus.service.impl;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.convert.BookConvertBookDto;
import ru.otus.convert.BookDtoConvertBook;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.exception.NotFoundException;
import ru.otus.exception.ValidationErrorException;
import ru.otus.repository.AuthorRepository;
import ru.otus.domain.Book;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookConvertBookDto convertBookDto;

    private final BookDtoConvertBook convertBook;

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertBookDto.convert(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(convertBookDto::convert).toList();
    }

    @Override
    @Transactional
    public BookDto create(BookDto book) {
        book.setComments(List.of());

        Book bookDomain = convertBook.convert(book);

        return convertBookDto.convert(
                save(bookDomain)
        );
    }

    @Override
    @Transactional
    public BookDto update(BookDto book) {
        if(Objects.isNull(book.getComments()))
            book.setComments(List.of());

        book.getComments().forEach(c -> c.setBook(book));

        Book bookDomain = convertBook.convert(book);

        return convertBookDto.convert(
                save(bookDomain)
        );
    }

    private Book save(Book book) {
        book.getAuthors().forEach(a -> checkValidation(a.getId()));
        book.getGenres().forEach(g -> checkValidation(g.getId()));

        List<Author> authors = authorRepository.findByIds(
                book.getAuthors().stream().map(Author::getId).toList()
        );

        checkExists(
                authors.stream().map(Author::getId).collect(Collectors.toSet()),
                book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet()));

        List<Genre> genres = genreRepository.findByIds(
                book.getGenres().stream().map(Genre::getId).toList()
        );

        checkExists(
                genres.stream().map(Genre::getId).collect(Collectors.toSet()),
                book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()));

        book.setAuthors(authors);
        book.setGenres(genres);

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(BookDto book) {
        bookRepository.deleteById(book.getId());
    }

    private void checkExists(Set<Long> ids1, Set<Long> ids2) {
        if (!equalsIds(ids1, ids2)) {
            throw new NotFoundException();
        }
    }

    private void checkValidation(Long id) {
        if (Objects.isNull(id)) {
            throw new ValidationErrorException("ID author is null");
        }
    }

    private boolean equalsIds(Set<Long> ids1, Set<Long> ids2) {
        return Sets.symmetricDifference(ids1, ids2).isEmpty();
    }
}