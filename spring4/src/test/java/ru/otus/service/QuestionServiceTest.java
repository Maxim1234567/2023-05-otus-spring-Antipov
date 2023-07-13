package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.domain.Answer;
import ru.otus.repository.QuestionDao;
import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;
import ru.otus.domain.UserData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test-en")
public class QuestionServiceTest {
    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private UserInteraction userInteraction;

    @Autowired
    private QuestionService questionService;

    private List<TestQuestion> questions;

    @BeforeEach
    public void setUp() {
        questions = List.of(
                new TestQuestion(
                        "Test Answer 1?",
                        List.of(new Answer("1", false), new Answer("1", false), new Answer("1", false), new Answer("4", true))
                ),
                new TestQuestion(
                        "Test Answer 2?",
                        List.of(new Answer("2", false), new Answer("2", false), new Answer("5", true))
                ),
                new TestQuestion(
                        "Test Answer 3?",
                        List.of(new Answer("3", false), new Answer("6", true))
                )
        );
    }

    @Test
    public void enterFirstAndLastNameUser() {
        given(userInteraction.createUser())
                .willReturn(new UserData("Maxim", "Antipov"));

        UserData userData = questionService.fillUserData();

        verify(userInteraction, times(1)).createUser();

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