package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    @Query("{'_id': {$in: :#{#ids}}}")
    List<Genre> findByIds(@Param("ids") List<String> ids);
}