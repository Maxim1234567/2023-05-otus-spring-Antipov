package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Document(collection = "passports")
public class Passport {
    @Id
    private String id;

    private String series;

    private String number;

    private String issued;

    private String dateOfIssue;

    private String codeDivision;
}
