package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.domain.Result;
import ru.otus.domain.UserData;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test-en")
public class DemoInteractionTest {
    @MockBean
    private QuestionService questionService;
    @MockBean
    private  IOService ioService;

    @MockBean
    private ApplicationMessageSource messageSource;

    @Autowired
    private DemoInteraction demoInteraction;

    private UserData userData;
    private List<Result> results;

    @BeforeEach
    public void setUp() {
        userData = new UserData("Maxim", "Antipov");
        results = List.of(
                new Result("Question 1?", "Answer user 1", "Correct answer 1"),
                new Result("Question 2?", "Answer user 2", "Correct answer 2"),
                new Result("Question 3?", "Answer user 3", "Correct answer 3")
        );
    }

    @Test
    public void interactionTest() {
        given(questionService.fillUserData())
                .willReturn(userData);
        given(questionService.askUserQuestions())
                .willReturn(results);
        given(messageSource.getMessage(eq("question")))
                .willReturn("Question");
        given(messageSource.getMessage(eq("answer.user")))
                .willReturn("Your Answer");
        given(messageSource.getMessage(eq("answer.correct")))
                .willReturn("Correct Answer");

        demoInteraction.interaction();

        verify(ioService, times(1 + results.size()))
                .println(eq(""));
        verify(ioService, times(1))
                .println(eq(userData.getFirstName() + " " + userData.getLastName()));

        results.forEach(result -> {
            verify(ioService, times(1))
                    .println(eq("Question: " + result.getQuestion()));
            verify(ioService, times(1))
                    .println(eq("Your Answer: " + result.getAnswerUser()));
            verify(ioService, times(1))
                    .println(eq("Correct Answer: " + result.getCorrectAnswer()));
        });
    }
}
