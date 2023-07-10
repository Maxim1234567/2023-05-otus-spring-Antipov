package ru.otus.dao;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

public interface BookDaoJdbc extends DaoJdbc<Book> {
    List<Book> findBookByGenreId(long genreId);
    void saveBookAndGenre(long bookId, List<Long> genreIds);
    void saveBookAndAuthors(long bookId, List<Long> authorIds);
}
