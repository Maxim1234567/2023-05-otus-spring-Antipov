package ru.otus.repository;

import ru.otus.domain.TestQuestion;

import java.util.List;

public interface CsvQuestionDao {
    List<TestQuestion> getAllQuestions();
}
