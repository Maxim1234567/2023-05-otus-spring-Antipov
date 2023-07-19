package ru.otus.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private int yearBirthdate;
}
