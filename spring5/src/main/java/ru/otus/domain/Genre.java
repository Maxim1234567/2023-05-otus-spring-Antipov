package ru.otus.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@EqualsAndHashCode
public class Genre {
    private final Long id;

    private final String genre;
}
