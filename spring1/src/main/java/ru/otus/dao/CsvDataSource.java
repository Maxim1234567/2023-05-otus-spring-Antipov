package ru.otus.dao;

import lombok.Getter;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.*;

@Getter
public class CsvDataSource {
    private List<Question> questions = new ArrayList<>();

    public CsvDataSource(String nameFileQuestions, String delimiter) {
        try(Scanner scanner = new Scanner(Objects.requireNonNull(CsvDataSource.class.getResourceAsStream(nameFileQuestions)), "UTF-8")) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] data = line.split(delimiter);

                String question = data[0];
                List<Answer> answers = Arrays.stream(data).skip(1).map(Answer::new).toList();

                questions.add(new Question(question, answers));
            }
        }
    }

    public Question getQuestionByNumber(int number) {
        try {
            return questions.get(number);
        } catch (IndexOutOfBoundsException e) {
            throw new QuestionNotFoundException();
        }
    }
}
