package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    @Query("{'_id': {$in: :#{#ids}}}")
    List<Author> findByIds(@Param("ids") List<String> ids);
}