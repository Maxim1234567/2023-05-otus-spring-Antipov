package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.domain.TestQuestion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(MockitoExtension.class)
public class  CsvQuestionDaoImplTest {
//    @Autowired(required = true)
     private CsvQuestionDao csvQuestionDao;
    private List<TestQuestion> questions;

//    @BeforeEach
    void setUp() {
        csvQuestionDao = new CsvQuestionDaoImpl("data.csv", ";");
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

//    @Test
    void correctParseFileCsv() {
        List<TestQuestion> result = csvQuestionDao.getAllQuestions();
        assertEquals(result, questions);
    }
}
