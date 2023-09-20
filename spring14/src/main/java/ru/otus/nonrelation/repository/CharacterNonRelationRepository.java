package ru.otus.nonrelation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.nonrelation.domain.CharacterNonRelation;

public interface CharacterNonRelationRepository extends MongoRepository<CharacterNonRelation, String> {
}
