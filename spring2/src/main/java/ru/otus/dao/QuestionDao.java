package ru.otus.dao;

import ru.otus.domain.TestQuestion;

import java.util.List;

public interface QuestionDao {
    List<TestQuestion> getAllQuestions();
}
