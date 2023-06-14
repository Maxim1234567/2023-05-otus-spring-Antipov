package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.dao.CsvQuestionDao;
import ru.otus.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @Mock
    private ConvertTestQuestionService convertTestQuestionService;

    @Mock
    private CsvQuestionDao csvQuestionDao;

    @Mock
    private UserInteraction userInteraction;

    @Mock
    private IOService ioService;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private List<TestQuestion> questions;

    @BeforeEach
    public void setUp() {
        questions = List.of(
                new TestQuestion(
                        "Test Answer 1?",
                        List.of("1", "1", "1"),
                        "1"
                ),
                new TestQuestion(
                        "Test Answer 2?",
                        List.of("2", "2"),
                        "2"
                ),
                new TestQuestion(
                        "Test Answer 3?",
                        List.of("3"),
                        "3"
                )
        );
    }

    @Test
    public void enterFirstAndLastNameUser() {
        given(userInteraction.askFirstName())
                .willReturn("Maxim");

        given(userInteraction.askLastName())
                .willReturn("Antipov");

        UserData userData = questionService.fillUserData();

        verify(userInteraction, times(1)).askFirstName();
        verify(userInteraction, times(1)).askLastName();

        assertEquals(userData.getFirstName(), "Maxim");
        assertEquals(userData.getLastName(), "Antipov");

        assertNotNull(userData);
        assertNotNull(userData.getFirstName());
        assertNotNull(userData.getLastName());
    }

    @Test
    public void askQuestion() {
        given(csvQuestionDao.getAllQuestions())
                .willReturn(questions);

        List<Result> result = questionService.askUserQuestions();

        verify(userInteraction, times(questions.size())).askQuestion(any(TestQuestion.class));

        assertNotNull(result);
        assertEquals(result.size(), questions.size());
    }
}
