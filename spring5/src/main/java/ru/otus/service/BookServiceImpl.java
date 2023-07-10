package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDaoJdbc;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.dao.GenreDaoJdbc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDaoJdbc bookDao;
    private final GenreDaoJdbc genreDao;
    private final AuthorDaoJdbc authorDao;

    @Override
    public Book getBookById(long id) {
        Book book = bookDao.getById(id);
        List<Genre> genres = genreDao.findGenreByBookId(id);
        List<Author> authors = authorDao.findAuthorByBookId(id);

        book.setGenres(genres);
        book.setAuthors(authors);

        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookDao.getAll();

        for (Book book: books) {
            List<Genre> genres = genreDao.findGenreByBookId(book.getId());
            List<Author> authors = authorDao.findAuthorByBookId(book.getId());

            book.setGenres(genres);
            book.setAuthors(authors);
        }

        return books;
    }

    @Override
    public Book save(Book book) {
        List<Long> authorIds = new ArrayList<>();
        List<Long> genreIds = new ArrayList<>();

        for(Genre genre: book.getGenres()) {
            Genre newGenre = genreDao.insert(genre);
            genreIds.add(newGenre.getId());
        }

        for(Author author: book.getAuthors()) {
            Author newAuthor = authorDao.insert(author);
            authorIds.add(newAuthor.getId());
        }

        Book newBook = bookDao.insert(book);

        bookDao.saveBookAndAuthors(newBook.getId(), authorIds);
        bookDao.saveBookAndGenre(newBook.getId(), genreIds);

        return getBookById(newBook.getId());
    }

    @Override
    public void delete(Book book) {
        bookDao.deleteById(book.getId());
    }

    @Override
    public Book update(Book book) {
        authorDao.deleteAuthorByBookId(book.getId());
        genreDao.deleteGenreByBookId(book.getId());

        Book updateBook = bookDao.update(book);

        List<Long> authorIds = book.getAuthors().stream().map(Author::getId).toList();
        List<Long> genreIds = book.getGenres().stream().map(Genre::getId).toList();

        bookDao.saveBookAndAuthors(book.getId(), authorIds);
        bookDao.saveBookAndGenre(book.getId(), genreIds);

        return getBookById(updateBook.getId());
    }
}
