package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
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
    private static ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
                List.of("1", "1", "1"),
                "4"
        );

        Result result1 = new Result(
                question1.getQuestion(),
                "1",
                question1.getCorrectAnswer()
        );

        TestQuestion question2 = new TestQuestion(
                "Test Answer 2?",
                List.of("2", "2"),
                "5"
        );

        Result result2 = new Result(
                question2.getQuestion(),
                "2",
                question2.getCorrectAnswer()
        );

        TestQuestion question3 = new TestQuestion(
                "Test Answer 3?",
                List.of("3"),
                "6"
        );

        Result result3 = new Result(
                question3.getQuestion(),
                "3",
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
