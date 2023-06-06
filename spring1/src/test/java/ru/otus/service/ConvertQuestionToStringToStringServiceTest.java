package ru.otus.service;

import org.junit.jupiter.api.Test;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConvertQuestionToStringToStringServiceTest {
    private final String QUESTION = "Test Question?";
    private final String ANSWER1 = "Test Answer 1";
    private final String ANSWER2 = "Test Answer 2";
    private final String ANSWER3 = "Test Answer 3";

    private ConvertQuestionService convertQuestionService
            = new ConvertQuestionServiceImpl();
    private Question question = new Question(
            QUESTION,
            List.of(new Answer(ANSWER1), new Answer(ANSWER2), new Answer(ANSWER3))
    );

    @Test
    void correctConvertQuestionToString() {
        String result = convertQuestionService.convert(question);

        assertTrue(result.contains(QUESTION));
        assertTrue(result.contains(ANSWER1));
        assertTrue(result.contains(ANSWER2));
        assertTrue(result.contains(ANSWER3));
    }
}
