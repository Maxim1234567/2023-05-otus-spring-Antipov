package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    private final int BIG_INDEX = Integer.MAX_VALUE;

    @Mock
    private ConvertQuestionToStringService convertQuestionToStringService;
    @Mock
    private QuestionDao questionDao;
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(convertQuestionToStringService, questionDao);
    }

    @Test
    void notCorrectIndex() {
        given(questionDao.findQuestionByNumber(BIG_INDEX))
                .willThrow(QuestionNotFoundException.class);

        assertThrows(
                QuestionNotFoundException.class,
                () -> questionService.askQuestion(BIG_INDEX)
        );
    }
}
