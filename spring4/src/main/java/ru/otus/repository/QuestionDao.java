package ru.otus.repository;

import ru.otus.domain.TestQuestion;

import java.util.List;

public interface QuestionDao {
    List<TestQuestion> getAllQuestions();
}
