package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.domain.Answer;
import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;
import ru.otus.domain.UserData;
import ru.otus.service.DemoInteraction;
import ru.otus.service.IOService;
import ru.otus.service.IOServiceStreams;
import ru.otus.service.UserInteractionImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test-en")
public class DemoApplicationTest {
    private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private static final String TEXT_WILL_BE = "\n" +
            "Maxim Antipov\n" +
            "Question: Test Answer 1?\n" +
            "Your Answer: 1\n" +
            "Correct Answer: 4\n" +
            "\n" +
            "Question: Test Answer 2?\n" +
            "Your Answer: 2\n" +
            "Correct Answer: 5\n" +
            "\n" +
            "Question: Test Answer 3?\n" +
            "Your Answer: 3\n" +
            "Correct Answer: 6\n" +
            "\n";

    @MockBean
    private UserInteractionImpl userInteraction;

    @Autowired
    private DemoInteraction interaction;

    @TestConfiguration
    static class DemoTestConfiguration {
        @Bean
        public IOService ioService() {
            return new IOServiceStreams(new PrintStream(baos), System.in);
        }
    }

    @BeforeEach
    public void setUp() {
        TestQuestion question1 = new TestQuestion(
                "Test Answer 1?",
                List.of(new Answer("1", false), new Answer("1", false), new Answer("1", false), new Answer("4", true))
        );

        Result result1 = new Result(
                question1.getQuestion(),
                new Answer("1", false),
                question1.getCorrectAnswer()
        );

        TestQuestion question2 = new TestQuestion(
                "Test Answer 2?",
                List.of(new Answer("2", false), new Answer("2", false), new Answer("5", true))
        );

        Result result2 = new Result(
                question2.getQuestion(),
                new Answer("2", false),
                question2.getCorrectAnswer()
        );

        TestQuestion question3 = new TestQuestion(
                "Test Answer 3?",
                List.of(new Answer("3", false), new Answer("6", true))
        );

        Result result3 = new Result(
                question3.getQuestion(),
                new Answer("3", false),
                question3.getCorrectAnswer()
        );

        given(userInteraction.createUser())
                .willReturn(new UserData("Maxim", "Antipov"));

        given(userInteraction.askQuestion(eq(question1)))
                .willReturn(result1);
        given(userInteraction.askQuestion(eq(question2)))
                .willReturn(result2);
        given(userInteraction.askQuestion(eq(question3)))
                .willReturn(result3);
    }

    @Test
    public void shouldHaveCorrectOutput() throws UnsupportedEncodingException {
        interaction.interaction();
        assertEquals(baos.toString().replace("\r", ""), TEXT_WILL_BE);
    }
}