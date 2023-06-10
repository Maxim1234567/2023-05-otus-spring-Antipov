package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserInteractionTest {
    @Mock
    private ConvertTestQuestionService convert;

    @Mock
    private IOService ioService;

    private UserInteraction userInteraction;

    @BeforeEach
    public void setUp() {
        userInteraction = new UserInteractionImpl(
                convert,
                ioService
        );
    }

    @Test
    public void askFirstNameTest() {
        given(ioService.readLine())
                .willReturn("Maxim");

        assertThat(userInteraction.askFirstName())
                .isEqualTo("Maxim");

        verify(ioService, times(1))
                .readLine();
    }

    @Test
    public void askLastNameTest() {
        given(ioService.readLine())
                .willReturn("Antipov");

        assertThat(userInteraction.askLastName())
                .isEqualTo("Antipov");

        verify(ioService, times(1))
                .readLine();
    }

    @Test
    public void askQuestionTest() {
        TestQuestion testQuestion = new TestQuestion(
                "Test Question 1?",
                List.of("Answer 1", "Answer 2", "Correct Answer"),
                "Correct Answer"
        );

        given(convert.convert(any(TestQuestion.class)))
                .willReturn(testQuestion.getQuestion());
        given(ioService.readLine())
                .willReturn("User Answer");

        var result = new Result(
                testQuestion.getQuestion(),
                "User Answer",
                testQuestion.getCorrectAnswer()
        );

        var response = userInteraction.askQuestion(testQuestion);

        assertEquals(result, response);

        verify(ioService, times(1))
                .println(eq(testQuestion.getQuestion()));
        verify(ioService, times(1))
                .print(anyString());
    }
}

/*
@Service
@RequiredArgsConstructor
public class UserInteractionImpl implements UserInteraction {
    @Override
    public Result askQuestion(TestQuestion question) {
        ioService.println(convert.convert(question));
        ioService.print("Enter your answer: ");

        return new Result(
                question.getQuestion(),
                new Answer(ioService.readLine()),
                question.getCorrectAnswer()
        );
    }
}
*/
