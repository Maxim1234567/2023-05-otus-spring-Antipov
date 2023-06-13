package ru.otus.service;

import ru.otus.domain.UserData;
import ru.otus.domain.Result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
            ioService.println("");
        });
    }
}
