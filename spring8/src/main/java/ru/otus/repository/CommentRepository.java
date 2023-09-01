package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query("{'_id': {$in: :#{#ids}}}")
    List<Comment> findByIds(@Param("ids") List<String> ids);

    List<Comment> findAllByBookId(String bookId);
}