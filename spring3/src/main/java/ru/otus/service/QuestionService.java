package ru.otus.service;

import ru.otus.domain.Result;
import ru.otus.domain.UserData;

import java.util.List;

public interface QuestionService {
    void showAllQuestion();
    UserData fillUserData();
    List<Result> askUserQuestions();
}
