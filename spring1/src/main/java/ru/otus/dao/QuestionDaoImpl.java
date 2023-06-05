package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {
    private final CsvDataSource dataSource;

    @Override
    public List<Question> getAllQuestions() {
        return dataSource.getAllQuestions();
    }
}
