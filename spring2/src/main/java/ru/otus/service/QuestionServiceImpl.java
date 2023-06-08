package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
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

    @Override
    public void showAllQuestion() {
        questionDao.getAllQuestions().forEach(q ->
                System.out.println(convertTestQuestionService.convert(q))
        );
    }

    @Override
    public UserData fillUserData() {
        String firstName = userInteraction.askFirstName();
        String lastName = userInteraction.askLastName();

        return new UserData(firstName, lastName);
    }

    @Override
    public List<Result> askUserQuestions() {
        List<Result> results = new ArrayList<>();

        questionDao.getAllQuestions().forEach(question -> results.add(userInteraction.askQuestion(question)));

        return results;
    }
}
