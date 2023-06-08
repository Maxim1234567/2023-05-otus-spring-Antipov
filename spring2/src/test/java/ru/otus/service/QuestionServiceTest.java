package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.dao.QuestionDao;
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
    private QuestionDao questionDao;

    @Mock
    private UserInteraction userInteraction;

    private QuestionService questionService;

    private List<TestQuestion> questions;

    @BeforeEach
    public void setUp() {
        questionService = new QuestionServiceImpl(
                convertTestQuestionService,
                questionDao,
                userInteraction
        );

        questions = List.of(
                new TestQuestion(
                        new Question("Test Answer 1?"),
                        List.of(new Answer("1"), new Answer("1"), new Answer("1")),
                        new Answer("1")
                ),
                new TestQuestion(
                        new Question("Test Answer 2?"),
                        List.of(new Answer("2"), new Answer("2")),
                        new Answer("2")
                ),
                new TestQuestion(
                        new Question("Test Answer 3?"),
                        List.of(new Answer("3")),
                        new Answer("3")
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
        given(questionDao.getAllQuestions())
                .willReturn(questions);

        List<Result> result = questionService.askUserQuestions();

        verify(userInteraction, times(questions.size())).askQuestion(any(TestQuestion.class));

        assertNotNull(result);
        assertEquals(result.size(), questions.size());
    }
}
