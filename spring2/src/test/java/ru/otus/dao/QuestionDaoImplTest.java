package ru.otus.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.domain.TestQuestion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class QuestionDaoImplTest {
     private QuestionDao questionDao;
    private List<TestQuestion> questions;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl("data.csv", ";");
        questions = List.of(
                new TestQuestion(
                        new Question("Test Answer 1?"),
                        List.of(new Answer("1"), new Answer("1"), new Answer("1")),
                        new Answer("4")
                ),
                new TestQuestion(
                        new Question("Test Answer 2?"),
                        List.of(new Answer("2"), new Answer("2")),
                        new Answer("5")
                ),
                new TestQuestion(
                        new Question("Test Answer 3?"),
                        List.of(new Answer("3")),
                        new Answer("6")
                )
        );
    }

    @Test
    void correctParseFileCsv() {
        List<TestQuestion> result = questionDao.getAllQuestions();
        assertEquals(result, questions);
    }
}
