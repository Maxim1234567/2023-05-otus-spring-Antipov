package ru.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {

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
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
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
        findById(id).ifPresent(em::remove);
    }
}