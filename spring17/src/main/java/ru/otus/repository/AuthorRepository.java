package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.domain.Author;

import java.util.List;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a where a.id in (:ids)")
    List<Author> findByIds(@Param("ids") List<Long> ids);

    @RestResource(path = "book-id", rel = "book-id")
    @Query("select a from Book b join b.authors a where b.id = :bookId")
    List<Author> findByBookId(@Param("bookId") long bookId);

    @Query("select a from Book b join b.authors a where b.id in (:bookIds)")
    List<Author> findByBookIds(@Param("bookIds") List<Long> bookIds);
}