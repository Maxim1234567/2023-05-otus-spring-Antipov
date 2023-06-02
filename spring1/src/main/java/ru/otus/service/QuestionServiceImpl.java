package ru.otus.service;

import lombok.RequiredArgsConstructor;
import ru.otus.dao.QuestionDao;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final ConvertQuestionToStringService convertQuestionToStringService;

    private final QuestionDao questionDao;

    @Override
    public void askQuestion(int number) {
        System.out.println(
                convertQuestionToStringService.questionToString(
                        questionDao.findQuestionByNumber(number)
                )
        );
    }

    @Override
    public void showAllQuestion() {
        questionDao.getAllQuestions().forEach(q ->
                System.out.println(convertQuestionToStringService.questionToString(q))
        );
    }
}
