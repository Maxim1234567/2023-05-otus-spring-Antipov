package ru.otus.service;

import lombok.RequiredArgsConstructor;
import ru.otus.repository.QuestionDao;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final ConvertQuestionService convertQuestionService;

    private final QuestionDao questionDao;

    @Override
    public void showAllQuestion() {
        questionDao.getAllQuestions().forEach(q ->
                System.out.println(convertQuestionService.convert(q))
        );
    }
}
