package ru.otus.relation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "character", schema = "public")
@Entity
@ToString
public class CharacterRelation {
    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String colorSkin;

    private boolean isDisabled;
}
