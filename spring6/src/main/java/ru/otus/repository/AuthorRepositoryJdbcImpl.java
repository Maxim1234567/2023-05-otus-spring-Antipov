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
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbcImpl implements AuthorRepositoryJdbc {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author save(Author author) {
        if(Objects.isNull(author.getId())) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Author findById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> findByIds(List<Long> ids) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.id in (:ids)", Author.class);
        query.setParameter("ids", ids);

        return query.getResultList();
    }

    @Override
    public List<Author> findByBookId(long bookId) {
        return findByBookIds(List.of(bookId));
    }

    @Override
    public List<Author> findByBookIds(List<Long> bookIds) {
        TypedQuery<Author> query = em.createQuery(
                "select a from Book b join b.authors a where b.id in (:bookIds)",
                Author.class
        );
        query.setParameter("bookIds", bookIds);

        return query.getResultList();
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Author author = findById(id);
        em.remove(author);
    }
}