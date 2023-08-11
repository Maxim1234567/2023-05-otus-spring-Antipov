package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.domain.Answer;
import ru.otus.domain.Result;
import ru.otus.domain.UserData;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest
//@ActiveProfiles("test-en")
public class DemoInteractionTest {
//    @MockBean
    private QuestionService questionService;
//    @Autowired
    private DemoInteraction demoInteraction;

    private UserData userData;
    private List<Result> results;

//    @BeforeEach
    public void setUp() {
        userData = new UserData("Maxim", "Antipov");
        results = List.of(
                new Result("Question 1?", new Answer("Answer user 1", false), new Answer("Correct answer 1", true)),
                new Result("Question 2?", new Answer("Answer user 1", false), new Answer("Correct answer 1", true)),
                new Result("Question 3?", new Answer("Answer user 1", false), new Answer("Correct answer 1", true))
        );
    }

//    @Test
    public void interactionTest() {
        given(questionService.fillUserData())
                .willReturn(userData);
        given(questionService.askUserQuestions())
                .willReturn(results);

        demoInteraction.interaction();

        verify(questionService, times(1))
                .printResult(eq(userData), eq(results));
    }
}