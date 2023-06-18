package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test-ru")
public class ApplicationMessageSourceRuTest {
    @Autowired
    private ApplicationMessageSource messageSource;

    @Test
    public void getFileQuestionTest() {
        assertEquals(messageSource.getFileQuestion(), "question-test-ru.csv");
    }

    @Test
    public void getTextQuestionTest() {
        assertEquals(messageSource.getTextQuestion(), "Вопрос");
    }

    @Test
    public void getTextAnswerUserTest() {
        assertEquals(messageSource.getTextAnswerUser(), "Ответ пользователя");
    }

    @Test
    public void getTextAnswerCorrectTest() {
        assertEquals(messageSource.getTextAnswerCorrect(), "Корректный ответ");
    }

    @Test
    public void getTextUserNameTest() {
        assertEquals(messageSource.getTextUserName(), "Введите имя: ");
    }

    @Test
    public void getTextUserLastnameTest() {
        assertEquals(messageSource.getTextUserLastname(), "Введите фамилию: ");
    }

    @Test
    public void getTextUserAnswerTest() {
        assertEquals(messageSource.getTextUserAnswer(), "Введите ответ: ");
    }
}
