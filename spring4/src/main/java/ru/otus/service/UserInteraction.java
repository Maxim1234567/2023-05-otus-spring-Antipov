package ru.otus.service;

import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;
import ru.otus.domain.UserData;

public interface UserInteraction {

    UserData createUser();

    Result askQuestion(TestQuestion question);
}
