package ru.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJdbcImpl implements GenreRepositoryJdbc {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if(Objects.isNull(genre.getId())) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Genre findById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> findByIds(List<Long> ids) {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Genre g where g.id in (:ids)",
                Genre.class
        );
        query.setParameter("ids", ids);

        return query.getResultList();
    }

    @Override
    public List<Genre> findByBookId(long bookId) {
        return findByBookIds(List.of(bookId));
    }

    @Override
    public List<Genre> findByBookIds(List<Long> bookIds) {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Book b join b.genres g where b.id in (:bookIds)",
                Genre.class
        );
        query.setParameter("bookIds", bookIds);

        return query.getResultList();
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Genre genre = findById(id);
        em.remove(genre);
    }
}