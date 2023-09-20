package ru.otus.relation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.relation.domain.CharacterRelation;

public interface CharacterRelationalRepository extends JpaRepository<CharacterRelation, Long> {
    @Query(value = "select nextval('character_seq')", nativeQuery = true)
    Long getIdNextVal();
}
