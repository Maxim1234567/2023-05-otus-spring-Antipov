package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.domain.Result;
import ru.otus.domain.UserData;
import ru.otus.props.ApplicationProperties;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoInteractionImpl implements DemoInteraction {
    private final QuestionService questionService;
    private final IOService ioService;
    private final ApplicationMessageSource messageSource;
    @Override
    public void interaction() {
        UserData userData = questionService.fillUserData();
        List<Result> results = questionService.askUserQuestions();

        ioService.println("");
        ioService.println(userData.getFirstName() + " " + userData.getLastName());
        results.forEach(result -> {
            ioService.println(messageSource.getTextQuestion() + ": " + result.getQuestion());
            ioService.println(messageSource.getTextAnswerUser() + ": " + result.getAnswerUser());
            ioService.println(messageSource.getTextAnswerCorrect() + ": " + result.getCorrectAnswer());
            ioService.println("");
        });
    }
}
