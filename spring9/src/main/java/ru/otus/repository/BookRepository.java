package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(value = "author-entity-graph")
    @Query("select b from Book b where b.id in (:ids)")
    List<Book> findByIds(@Param("ids") List<Long> ids);

    @EntityGraph(value = "author-entity-graph")
    List<Book> findAll();
}