package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.convert.ConvertTestQuestionService;
import ru.otus.domain.Answer;
import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest
//@ActiveProfiles("test-en")
public class UserInteractionTest {
//    @MockBean
    private ConvertTestQuestionService convert;

//    @MockBean
    private IOService ioService;

//    @MockBean
    private ApplicationMessageSource messageSource;

//    @Autowired
    private UserInteraction userInteraction;

//    @Test
    public void createUserTest() {
        userInteraction.createUser();

        verify(ioService, times(2)).readLine();
    }

//    @Test
    public void askQuestionTest() {
        TestQuestion testQuestion = new TestQuestion(
                "Test Question 1?",
                List.of(new Answer("Answer 1", false), new Answer("Answer 2", false), new Answer("Correct Answer", false), new Answer("Correct Answer", true))
        );

        given(convert.convert(any(TestQuestion.class)))
                .willReturn(testQuestion.getQuestion());
        given(ioService.readLine())
                .willReturn("User Answer");
        given(messageSource.getMessage(eq("user.answer")))
                .willReturn("Enter your answer: ");

        var result = new Result(
                testQuestion.getQuestion(),
                new Answer("User Answer", false),
                testQuestion.getCorrectAnswer()
        );

        var response = userInteraction.askQuestion(testQuestion);

        assertEquals(result, response);

        verify(ioService, times(1))
                .println(anyString());
        verify(ioService, times(1))
                .print(anyString());
    }
}