package ru.otus.nonrelation.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Document(collection = "episodes")
public class EpisodeNonRelation {
    @Id
    private String id;

    private String name;

    private int season;

    private int episode;

    private List<CharacterNonRelation> characterNonRelations;
}
