package ru.otus.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Result {
    private final String question;

    private final Answer userAnswer;

    private final Answer correctAnswer;
}
