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
        assertEquals(messageSource.getMessage("file-question"), "/question-test-en.csv");
    }

    @Test
    public void getTextQuestionTest() {
        assertEquals(messageSource.getMessage("question"), "Question");
    }

    @Test
    public void getTextAnswerUserTest() {
        assertEquals(messageSource.getMessage("answer.user"), "Your Answer");
    }

    @Test
    public void getTextAnswerCorrectTest() {
        assertEquals(messageSource.getMessage("answer.correct"), "Correct Answer");
    }

    @Test
    public void getTextUserNameTest() {
        assertEquals(messageSource.getMessage("user.name"), "Enter your First Name: ");
    }

    @Test
    public void getTextUserLastnameTest() {
        assertEquals(messageSource.getMessage("user.lastname"), "Enter your Last Name: ");
    }

    @Test
    public void getTextUserAnswerTest() {
        assertEquals(messageSource.getMessage("user.answer"), "Enter your answer: ");
    }
}
