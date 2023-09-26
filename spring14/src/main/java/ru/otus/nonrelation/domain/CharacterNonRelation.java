package ru.otus.nonrelation.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Document(collection = "characters")
@ToString
public class CharacterNonRelation {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String colorSkin;

    private boolean isDisabled;
}
