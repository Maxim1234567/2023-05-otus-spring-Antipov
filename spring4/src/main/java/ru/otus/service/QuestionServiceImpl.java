package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.convert.ConvertResult;
import ru.otus.convert.ConvertTestQuestionService;
import ru.otus.repository.QuestionDao;
import ru.otus.domain.Result;
import ru.otus.domain.UserData;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {
    private final ConvertTestQuestionService convertTestQuestionService;

    private final QuestionDao questionDao;

    private final UserInteraction userInteraction;

    private final IOService ioService;
    private final ConvertResult convertResult;

    @Override
    public void showAllQuestion() {
        questionDao.getAllQuestions().forEach(q ->
                ioService.println(convertTestQuestionService.convert(q))
        );
    }

    @Override
    public UserData fillUserData() {
        return userInteraction.createUser();
    }

    @Override
    public List<Result> askUserQuestions() {
        List<Result> results = new ArrayList<>();

        questionDao.getAllQuestions().forEach(question -> results.add(userInteraction.askQuestion(question)));

        return results;
    }

    @Override
    public void printResult(UserData userData, List<Result> results) {
        ioService.println("");
        ioService.println(userData.getFirstName() + " " + userData.getLastName());
        results.forEach(result -> ioService.print(convertResult.convert(result)));
    }
}
