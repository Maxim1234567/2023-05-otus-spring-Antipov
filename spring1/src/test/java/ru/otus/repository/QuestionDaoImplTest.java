package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(MockitoExtension.class)
public class QuestionDaoImplTest {
     private QuestionDao questionDao;
    private List<Question> questions;

//    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl("/data.csv", ";");
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

//    @Test
    void correctParseFileCsv() {
        List<Question> result = questionDao.getAllQuestions();
        assertEquals(result, questions);
    }
}
