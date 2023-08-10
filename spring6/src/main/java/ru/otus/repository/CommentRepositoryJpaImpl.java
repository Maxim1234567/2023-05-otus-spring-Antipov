package ru.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Comment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (Objects.isNull(comment.getId())) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findByIds(List<Long> ids) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.id in (:ids)",
                Comment.class
        );
        query.setParameter("ids", ids);

        return query.getResultList();
    }

    @Override
    public List<Comment> findAllByBookId(long bookId) {
        return em
                .createQuery("select c from Comment c where c.bookId = :bookId", Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
