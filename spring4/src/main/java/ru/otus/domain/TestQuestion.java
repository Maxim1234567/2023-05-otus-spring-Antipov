package ru.otus.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class TestQuestion {
    private final String question;

    private final List<Answer> answers;

    public Answer getCorrectAnswer() {
        return answers.stream().filter(Answer::isCorrect).findFirst().get();
    }
}
