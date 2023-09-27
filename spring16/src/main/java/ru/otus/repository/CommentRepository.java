package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.domain.Comment;

import java.util.List;

@RepositoryRestResource(path = "comment")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.id in (:ids)")
    List<Comment> findByIds(@Param("ids") List<Long> ids);

    @RestResource(path = "book-id", rel = "book-id")
    List<Comment> findAllByBookId(long bookId);
}