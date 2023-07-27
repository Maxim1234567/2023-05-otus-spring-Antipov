package ru.otus.service;

import org.junit.jupiter.api.Test;
import ru.otus.convert.ConvertAnswer;
import ru.otus.convert.ConvertAnswerImpl;
import ru.otus.convert.ConvertTestQuestionService;
import ru.otus.convert.ConvertTestQuestionServiceImpl;
import ru.otus.domain.Answer;
import ru.otus.domain.TestQuestion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConvertTestQuestionServiceTest {
    private final String QUESTION = "Test Question?";

    private final Answer ANSWER1 = new Answer("Test Answer 1", false);
    private final Answer ANSWER2 = new Answer("Test Answer 2", false);
    private final Answer ANSWER3 = new Answer("Test Answer 3", false);
    private final Answer CORRECT_ANSWER = new Answer("CORRECT ANSWER", true);

    private final String TEXT_ANSWER1 = "Test Answer 1";
    private final String TEXT_ANSWER2 = "Test Answer 2";
    private final String TEXT_ANSWER3 = "Test Answer 3";

    private final String TEXT_CORRECT_ANSWER = "CORRECT ANSWER";

    private ConvertAnswer convertAnswer = new ConvertAnswerImpl();
    private ConvertTestQuestionService convertTestQuestionService
            = new ConvertTestQuestionServiceImpl(convertAnswer);
    private TestQuestion question = new TestQuestion(
            QUESTION,
            List.of(ANSWER1, ANSWER2, ANSWER3, CORRECT_ANSWER)
    );

    @Test
    void correctConvertQuestionToString() {
        String result = convertTestQuestionService.convert(question);

        assertTrue(result.contains(QUESTION));
        assertTrue(result.contains(TEXT_ANSWER1));
        assertTrue(result.contains(TEXT_ANSWER2));
        assertTrue(result.contains(TEXT_ANSWER3));
        assertFalse(result.contains(TEXT_CORRECT_ANSWER));
    }
}
