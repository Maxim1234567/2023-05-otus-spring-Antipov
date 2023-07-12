package ru.otus.repository;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException() {
        super("Question not found");
    }
}
