package ru.otus.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.dao.CsvDataSource;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CsvDataSourceTest {
    private CsvDataSource csvDataSource;
    private List<Question> questions;

    @BeforeEach
    void setUp() {
        csvDataSource = new CsvDataSource("/data.csv", ";");
        questions = List.of(
                new Question("Test Answer 1?", List.of(
                        new Answer("1"),
                        new Answer("1"),
                        new Answer("1"))),
                new Question("Test Answer 2?", List.of(
                        new Answer("2"),
                        new Answer("2"))),
                new Question("Test Answer 3?", List.of(
                        new Answer("3")))
        );
    }

    @Test
    void correctParseFileCsv() {
        List<Question> result = csvDataSource.getQuestions();
        assertEquals(result, questions);
    }

    @Test
    void correctReturnQuestionByIndex() {
        Question question = csvDataSource.getQuestionByNumber(0);
        assertEquals(questions.get(0), question);
    }

    @Test
    void notCorrectIndex() {
        assertThrows(
                QuestionNotFoundException.class, () -> csvDataSource.getQuestionByNumber(questions.size())
        );
    }
}
