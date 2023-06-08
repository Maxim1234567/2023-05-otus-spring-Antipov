package ru.otus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Result {
    private final Question question;
    private final Answer answerUser;
    private final Answer correctAnswer;
}
