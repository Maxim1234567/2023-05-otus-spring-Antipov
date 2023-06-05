package ru.otus.service;


import ru.otus.domain.Question;

public interface ConvertQuestionService {
    String questionToString(Question question);
}
