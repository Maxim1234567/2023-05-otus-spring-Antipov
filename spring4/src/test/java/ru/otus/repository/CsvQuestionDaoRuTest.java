package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.domain.Answer;
import ru.otus.domain.TestQuestion;
import ru.otus.props.ApplicationProperties;
import ru.otus.service.ApplicationMessageSource;
import ru.otus.service.ApplicationMessageSourceImpl;
import ru.otus.service.ResourceProvider;
import ru.otus.service.ResourceProviderImpl;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvQuestionDaoRuTest {

    private QuestionDao questionDao;
    private List<TestQuestion> questions;

    @BeforeEach
    public void setUp() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/appmessages");
        messageSource.setDefaultEncoding("UTF-8");

        ApplicationProperties applicationProperties = new ApplicationProperties(
                ";", new Locale("ru"), "/question-test-ru.csv"
        );

        ResourceProvider resourceProvider = new ResourceProviderImpl(applicationProperties);

        questionDao = new CsvQuestionDao(resourceProvider);

        questions = List.of(
                new TestQuestion(
                        "Тестовый вопрос 1?",
                        List.of(new Answer("1", false), new Answer("1", false), new Answer("1", false), new Answer("4", true))
                ),
                new TestQuestion(
                        "Тестовый вопрос 2?",
                        List.of(new Answer("2", false), new Answer("2", false), new Answer("5", true))
                ),
                new TestQuestion(
                        "Тестовый вопрос 3?",
                        List.of(new Answer("3", false), new Answer("6", true))
                )
        );
    }

    @Test
    void correctParseFileCsv() {
        List<TestQuestion> result = questionDao.getAllQuestions();
        assertEquals(result, questions);
    }
}