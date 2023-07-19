package ru.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.h2.value.Typed;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.ext.BookAuthorRelation;
import ru.otus.repository.ext.BookGenreRelation;
import ru.otus.repository.ext.BookResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbcImpl implements BookRepositoryJdbc {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book save(Book book) {
        if(Objects.isNull(book.getId())) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findByIds(List<Long> ids) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.id in (:ids)",
                Book.class
        );
        query.setParameter("ids", ids);

        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooks() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }
}