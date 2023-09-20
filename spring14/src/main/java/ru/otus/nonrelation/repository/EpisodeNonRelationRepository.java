package ru.otus.nonrelation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.nonrelation.domain.EpisodeNonRelation;

public interface EpisodeNonRelationRepository extends MongoRepository<EpisodeNonRelation, String> {
}
