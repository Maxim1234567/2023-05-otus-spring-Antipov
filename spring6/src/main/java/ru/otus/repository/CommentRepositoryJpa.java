package ru.otus.repository;

import ru.otus.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa {
    Comment save(Comment comment);

    void deleteById(long id);

    Optional<Comment> findById(long id);

    List<Comment> findByIds(List<Long> ids);

    List<Comment> findAllByBookId(long bookId);
}
