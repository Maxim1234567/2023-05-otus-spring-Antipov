package ru.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpaImpl implements GenreRepositoryJpa {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if (Objects.isNull(genre.getId())) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
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
        findById(id).ifPresent(em::remove);
    }
}