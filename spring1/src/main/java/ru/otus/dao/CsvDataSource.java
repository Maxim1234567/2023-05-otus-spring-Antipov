package ru.otus.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;

@RequiredArgsConstructor
public class CsvDataSource {
    private final String nameFileQuestions;
    private final String delimiter;

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();

        try(Scanner scanner = new Scanner(
                Objects.requireNonNull(CsvDataSource.class.getResourceAsStream(nameFileQuestions)),
                "UTF-8")) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] data = line.split(delimiter);

                String question = data[0];
                List<Answer> answers = Arrays.stream(data).skip(1).map(Answer::new).toList();

                questions.add(new Question(question, answers));
            }
        }

        return questions;
    }
}
