package ru.otus.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.domain.TestQuestion;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Arrays;

@Repository
public class CsvQuestionDaoImpl implements CsvQuestionDao {
    private final String nameFileQuestions;

    private final String delimiter;

    public CsvQuestionDaoImpl(
            @Value("${name.file.questions}") String nameFileQuestions,
            @Value("${delimiter}") String delimiter) {
        this.nameFileQuestions = "/" + nameFileQuestions;
        this.delimiter = delimiter;
    }

    @Override
    public List<TestQuestion> getAllQuestions() {
        List<TestQuestion> testQuestions = new ArrayList<>();

        try(Scanner scanner = new Scanner(
                Objects.requireNonNull(CsvQuestionDaoImpl.class.getResourceAsStream(nameFileQuestions)),
                "UTF-8")) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] data = line.split(delimiter);

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
