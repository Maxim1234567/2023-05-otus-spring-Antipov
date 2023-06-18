package ru.otus.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.domain.TestQuestion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test-en")
public class CsvQuestionDaoEnTest {
    @Autowired
    private CsvQuestionDao csvQuestionDao;
    private List<TestQuestion> questions;

    @BeforeEach
    public void setUp() {
        questions = List.of(
                new TestQuestion(
                        "Test Answer 1?",
                        List.of("1", "1", "1"),
                        "4"
                ),
                new TestQuestion(
                        "Test Answer 2?",
                        List.of("2", "2"),
                        "5"
                ),
                new TestQuestion(
                        "Test Answer 3?",
                        List.of("3"),
                        "6"
                )
        );
    }

    @Test
    void correctParseFileCsv() {
        List<TestQuestion> result = csvQuestionDao.getAllQuestions();
        assertEquals(result, questions);
    }
}
