package ru.otus.repository;

import org.springframework.stereotype.Repository;
import ru.otus.domain.TestQuestion;
import lombok.RequiredArgsConstructor;
import ru.otus.domain.Answer;
import ru.otus.service.ResourceProvider;
import ru.otus.service.SettingsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Objects;
import java.util.Arrays;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final ResourceProvider resourceProvider;

    private final SettingsProvider settingsProvider;

    @Override
    public List<TestQuestion> getAllQuestions() {
        List<TestQuestion> testQuestions = new ArrayList<>();

        try (Scanner scanner = new Scanner(
                Objects.requireNonNull(CsvQuestionDao.class.getResourceAsStream(resourceProvider.getFileName())),
                "UTF-8"
        )) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] data = line.split(settingsProvider.getDelimiter());

                String question = data[0];
                List<String> textAnswers = Arrays.stream(data).skip(1).toList();
                List<Answer> answers = new ArrayList<>();

                for (int i = 0; i < textAnswers.size(); i++) {
                    Answer answer = new Answer(
                            textAnswers.get(i),
                            i == textAnswers.size() - 1
                    );

                    answers.add(answer);
                }

                testQuestions.add(
                        new TestQuestion(
                                question,
                                answers
                        )
                );
            }
        }

        return testQuestions;
    }
}
