package ru.otus.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Document(collection = "authors")
public class Author {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private int age;

    private int yearBirthdate;
}