package ru.otus.repository;

import org.springframework.stereotype.Repository;
import ru.otus.props.ApplicationProperties;
import ru.otus.domain.TestQuestion;
import ru.otus.service.ApplicationMessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Objects;


@Repository
public class CsvQuestionDaoImpl implements CsvQuestionDao {
    private final ApplicationMessageSource messageSource;

    private final ApplicationProperties props;

    private final String nameFileQuestions;

    public CsvQuestionDaoImpl(
            ApplicationMessageSource messageSource,
            ApplicationProperties props
    ) {
        this.messageSource = messageSource;
        this.props = props;
        this.nameFileQuestions = this.messageSource.getMessage("file-question");
    }

    @Override
    public List<TestQuestion> getAllQuestions() {
        List<TestQuestion> testQuestions = new ArrayList<>();

        try(Scanner scanner = new Scanner(
                Objects.requireNonNull(CsvQuestionDaoImpl.class.getResourceAsStream(nameFileQuestions)),
                "UTF-8"
        )) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] data = line.split(props.getDelimiter());

                String question = data[0];
                List<String> answers = Arrays.stream(data).skip(1).toList();

                testQuestions.add(
                        new TestQuestion(
                                question,
                                answers.subList(0, answers.size() - 1),
                                answers.get(answers.size() - 1)
                        )
                );
            }
        }

        return testQuestions;
    }
}
