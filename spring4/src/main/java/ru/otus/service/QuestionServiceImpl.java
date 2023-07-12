package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    private final ApplicationMessageSource messageSource;

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
        results.forEach(result -> {
            ioService.println(messageSource.getMessage("question") + ": " + result.getQuestion());
            ioService.println(messageSource.getMessage("answer.user") + ": " + result.getAnswerUser());
            ioService.println(messageSource.getMessage("answer.correct") + ": " + result.getCorrectAnswer());
            ioService.println("");
        });
    }
}
