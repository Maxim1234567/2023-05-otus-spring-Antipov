package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Result;
import ru.otus.domain.UserData;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoInteractionImpl implements DemoInteraction {
    private final QuestionService questionService;

    @Override
    public void interaction() {
        UserData userData = questionService.fillUserData();
        List<Result> results = questionService.askUserQuestions();

        questionService.printResult(userData, results);
    }
}
