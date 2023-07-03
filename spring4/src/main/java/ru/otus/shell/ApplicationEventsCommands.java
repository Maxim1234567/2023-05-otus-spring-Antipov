package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.domain.Result;
import ru.otus.domain.UserData;
import ru.otus.service.ApplicationMessageSource;
import ru.otus.service.DemoInteraction;
import ru.otus.service.IOService;
import ru.otus.service.QuestionService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {
    private final QuestionService questionService;

    private final IOService ioService;

    private final ApplicationMessageSource messageSource;

    private UserData userData;
    private List<Result> results;

    @ShellMethod(value = "Login Command", key = {"l", "login"})
    public String login() {
        userData = questionService.fillUserData();
        return "Logged In";
    }

    @ShellMethod(value = "Pass the Test command", key = {"p", "pass"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String passTest() {
        results = questionService.askUserQuestions();

        return "Test passed";
    }

    @ShellMethod(value = "Show result command", key = {"s", "show"})
    @ShellMethodAvailability(value = "isTestPassed")
    public String showResult() {
        ioService.println(userData.getFirstName() + " " + userData.getLastName());
        results.forEach(result -> {
            ioService.println(messageSource.getMessage("question") + ": " + result.getQuestion());
            ioService.println(messageSource.getMessage("answer.user") + ": " + result.getAnswerUser());
            ioService.println(messageSource.getMessage("answer.correct") + ": " + result.getCorrectAnswer());
            ioService.println("");
        });

        return "Result";
    }

    private Availability isTestPassed() {
        return results == null ? Availability.unavailable("Pass the test first") : Availability.available();
    }

    private Availability isPublishEventCommandAvailable() {
        return userData == null ? Availability.unavailable("Login first") : Availability.available();
    }
}
