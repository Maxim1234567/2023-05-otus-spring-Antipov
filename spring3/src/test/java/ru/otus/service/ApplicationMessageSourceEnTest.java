package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test-en")
public class ApplicationMessageSourceEnTest {
    @Autowired
    private ApplicationMessageSource messageSource;

    @Test
    public void getFileQuestionTest() {
        assertEquals(messageSource.getFileQuestion(), "question-test-en.csv");
    }

    @Test
    public void getTextQuestionTest() {
        assertEquals(messageSource.getTextQuestion(), "Question");
    }

    @Test
    public void getTextAnswerUserTest() {
        assertEquals(messageSource.getTextAnswerUser(), "Your Answer");
    }

    @Test
    public void getTextAnswerCorrectTest() {
        assertEquals(messageSource.getTextAnswerCorrect(), "Correct Answer");
    }

    @Test
    public void getTextUserNameTest() {
        assertEquals(messageSource.getTextUserName(), "Enter your First Name: ");
    }

    @Test
    public void getTextUserLastnameTest() {
        assertEquals(messageSource.getTextUserLastname(), "Enter your Last Name: ");
    }

    @Test
    public void getTextUserAnswerTest() {
        assertEquals(messageSource.getTextUserAnswer(), "Enter your answer: ");
    }
}
