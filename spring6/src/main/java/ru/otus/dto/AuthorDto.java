package ru.otus.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
