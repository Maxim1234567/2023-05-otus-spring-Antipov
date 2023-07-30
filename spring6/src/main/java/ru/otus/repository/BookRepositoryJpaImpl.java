package ru.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

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
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findByIds(List<Long> ids) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b left join b.comments where b.id in (:ids)",
                Book.class
        );
        query.setParameter("ids", ids);

        return query.getResultList();
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b left join b.comments", Book.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(em::remove);
    }
}