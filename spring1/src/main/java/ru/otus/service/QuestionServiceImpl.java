package ru.otus.service;

import lombok.RequiredArgsConstructor;
import ru.otus.dao.QuestionDao;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final ConvertQuestionService convertQuestionService;

    private final QuestionDao questionDao;

    @Override
    public void askQuestion(int number) {
        System.out.println(
                convertQuestionService.questionToString(
                        questionDao.findQuestionByNumber(number)
                )
        );
    }

    @Override
    public void showAllQuestion() {
        questionDao.getAllQuestions().forEach(q ->
                System.out.println(convertQuestionService.questionToString(q))
        );
    }
}
