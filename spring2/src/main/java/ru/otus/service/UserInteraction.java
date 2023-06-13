package ru.otus.service;

import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;

public interface UserInteraction {
    String askFirstName();

    String askLastName();

    Result askQuestion(TestQuestion question);
}
