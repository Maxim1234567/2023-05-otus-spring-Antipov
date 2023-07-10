package ru.otus.dao;

import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

public interface GenreDaoJdbc extends DaoJdbc<Genre> {
    List<Genre> findGenreByBookId(long bookId);
    void deleteGenreByBookId(long bookId);
}
