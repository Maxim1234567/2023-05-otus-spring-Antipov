package ru.otus.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.domain.TestQuestion;
import ru.otus.props.ApplicationProperties;
import ru.otus.service.ApplicationMessageSource;
import ru.otus.service.ApplicationMessageSourceImpl;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvQuestionDaoRuTest {

    private CsvQuestionDao csvQuestionDao;
    private List<TestQuestion> questions;

    @BeforeEach
    public void setUp() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/appmessages");
        messageSource.setDefaultEncoding("UTF-8");

        ApplicationProperties applicationProperties = new ApplicationProperties(
                ";", new Locale("ru")
        );

        ApplicationMessageSource applicationMessageSource = new ApplicationMessageSourceImpl(
                messageSource,
                applicationProperties
        );

        csvQuestionDao = new CsvQuestionDaoImpl(
                applicationMessageSource,
                applicationProperties
        );

        questions = List.of(
                new TestQuestion(
                        "Тестовый вопрос 1?",
                        List.of("1", "1", "1"),
                        "4"
                ),
                new TestQuestion(
                        "Тестовый вопрос 2?",
                        List.of("2", "2"),
                        "5"
                ),
                new TestQuestion(
                        "Тестовый вопрос 3?",
                        List.of("3"),
                        "6"
                )
        );
    }

    @Test
    void correctParseFileCsv() {
        List<TestQuestion> result = csvQuestionDao.getAllQuestions();
        assertEquals(questions, result);
    }
}
