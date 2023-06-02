package ru.otus.service;


import ru.otus.domain.Question;

public interface ConvertQuestionToStringService {
    String questionToString(Question question);
}
