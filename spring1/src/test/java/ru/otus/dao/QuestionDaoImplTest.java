package ru.otus.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QuestionDaoImplTest {
    private final int BIG_INDEX = Integer.MAX_VALUE;

    @Mock
    private CsvDataSource csvDataSource;
    private QuestionDao questionDao;

    private Question question;
    private List<Question> questions;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl(csvDataSource);
        question = new Question("Question", List.of(new Answer("Answer")));
        questions = List.of(new Question("Question1", List.of(new Answer("Answer1"))));
    }

    @Test
    void correctReturnAllQuestion() {
        given(csvDataSource.getQuestions())
                .willReturn(questions);

        assertThat(questionDao.getAllQuestions())
                .isEqualTo(questions);
    }

    @Test
    void correctReturnQuestionByIndex() {
        given(csvDataSource.getQuestionByNumber(eq(1)))
                .willReturn(question);

        assertThat(questionDao.findQuestionByNumber(1))
                .isEqualTo(question);
    }

    @Test
    void notCorrectIndex() {
        given(csvDataSource.getQuestionByNumber(eq(BIG_INDEX)))
                .willThrow(QuestionNotFoundException.class);

        assertThrows(
                QuestionNotFoundException.class,
                () -> questionDao.findQuestionByNumber(BIG_INDEX)
        );
    }
}
