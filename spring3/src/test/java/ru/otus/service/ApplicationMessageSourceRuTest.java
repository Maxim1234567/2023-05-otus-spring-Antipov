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
        assertEquals(messageSource.getMessage("file-question"), "/question-test-ru.csv");
    }

    @Test
    public void getTextQuestionTest() {
        assertEquals(messageSource.getMessage("question"), "Вопрос");
    }

    @Test
    public void getTextAnswerUserTest() {
        assertEquals(messageSource.getMessage("answer.user"), "Ответ пользователя");
    }

    @Test
    public void getTextAnswerCorrectTest() {
        assertEquals(messageSource.getMessage("answer.correct"), "Корректный ответ");
    }

    @Test
    public void getTextUserNameTest() {
        assertEquals(messageSource.getMessage("user.name"), "Введите имя: ");
    }

    @Test
    public void getTextUserLastnameTest() {
        assertEquals(messageSource.getMessage("user.lastname"), "Введите фамилию: ");
    }

    @Test
    public void getTextUserAnswerTest() {
        assertEquals(messageSource.getMessage("user.answer"), "Введите ответ: ");
    }
}
