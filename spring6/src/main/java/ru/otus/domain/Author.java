package ru.otus.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Data
@EqualsAndHashCode
public class Author {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final int yearBirthdate;
}