package ru.otus.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Book {
    private final Long id;

    private final String name;

    private final Integer yearIssue;

    private final Integer numberPages;

    private List<Genre> genres;

    private List<Author> authors;
}
