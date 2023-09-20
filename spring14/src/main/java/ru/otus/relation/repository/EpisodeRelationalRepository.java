package ru.otus.relation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.relation.domain.EpisodeRelation;

public interface EpisodeRelationalRepository extends JpaRepository<EpisodeRelation, Long> {
    @Query(value = "select nextval('episode_seq')", nativeQuery = true)
    Long getIdNextVal();
}
