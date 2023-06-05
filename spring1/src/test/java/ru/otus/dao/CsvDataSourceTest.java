package ru.otus.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<Question> result = csvDataSource.getAllQuestions();
        assertEquals(result, questions);
    }
}
