package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.domain.Result;
import ru.otus.domain.UserData;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@ExtendWith(MockitoExtension.class)
public class DemoInteractionTest {
//    @Mock
    private QuestionService questionService;
//    @Mock
    private  IOService ioService;
//    @InjectMocks
    private DemoInteractionImpl demoInteraction;

    private UserData userData;
    private List<Result> results;

//    @BeforeEach
    public void setUp() {
        userData = new UserData("Maxim", "Antipov");
        results = List.of(
                new Result("Question 1?", "Answer user 1", "Correct answer 1"),
                new Result("Question 2?", "Answer user 2", "Correct answer 2"),
                new Result("Question 3?", "Answer user 3", "Correct answer 3")
        );
    }

//    @Test
    public void interactionTest() {
        given(questionService.fillUserData())
                .willReturn(userData);
        given(questionService.askUserQuestions())
                .willReturn(results);

        demoInteraction.interaction();

        verify(ioService, times(1))
                .println(eq(userData.getFirstName() + " " + userData.getLastName()));

        results.forEach(result -> {
            verify(ioService, times(1))
                    .println(eq("Question: " + result.getQuestion()));
            verify(ioService, times(1))
                    .println(eq("Your Answer: " + result.getAnswerUser()));
            verify(ioService, times(1))
                    .println(eq("Correct Answer: " + result.getCorrectAnswer()));
        });
    }
}

/*
@RequiredArgsConstructor
@Service
public class DemoInteractionImpl implements DemoInteraction {
    private final QuestionService questionService;
    private final IOService ioService;

    @Override
    public void interaction() {
        UserData userData = questionService.fillUserData();
        List<Result> results = questionService.askUserQuestions();

        ioService.println("");
        ioService.println(userData.getFirstName() + " " + userData.getLastName());
        results.forEach(result -> {
            ioService.println("Question: " + result.getQuestion());
            ioService.println("Your Answer: " + result.getAnswerUser());
            ioService.println("Correct Answer: " + result.getCorrectAnswer());
            ioService.println();
        });
    }
}
*/