package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    @Query(value = "{'_id': :#{#bookId}}", fields = "{_id: 0, authors: 1}")
    Book findBookWithAuthorsById(@Param("bookId") String bookId);

    @Query(value = "{'_id': {$in: :#{#ids}}}", fields = "{_id: 0, authors: 1}")
    List<Book> findBookWithAuthorsByIds(@Param("ids") List<String> ids);

    @Query(value = "{'_id':  :#{#bookId}}", fields = "{_id: 0, genres:  1}")
    Book findBookWithGenresById(@Param("bookId") String bookId);

    @Query(value = "{'_id': {$in: :#{#ids}}}", fields = "{_id: 0, genres: 1}")
    List<Book> findBookWithGenresByIds(@Param("ids") List<String> ids);

    @Query("{'_id': {$in: :#{#ids}}}")
    List<Book> findByIds(@Param("ids") List<String> ids);
}