package ru.otus.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.domain.TestQuestion;

import java.util.*;

@Repository
public class QuestionDaoImpl implements QuestionDao {
    private final String nameFileQuestions;
    private final String delimiter;

    public QuestionDaoImpl(
            @Value("${name.file.questions}") String nameFileQuestions,
            @Value("${delimiter}") String delimiter
    ) {
        this.nameFileQuestions = "/" + nameFileQuestions;
        this.delimiter = delimiter;
    }

    @Override
    public List<TestQuestion> getAllQuestions() {
        List<TestQuestion> testQuestions = new ArrayList<>();

        try(Scanner scanner = new Scanner(
                Objects.requireNonNull(QuestionDaoImpl.class.getResourceAsStream(nameFileQuestions)),
                "UTF-8")) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] data = line.split(delimiter);

                String question = data[0];
                List<Answer> answers = Arrays.stream(data).skip(1).map(Answer::new).toList();

                testQuestions.add(
                        new TestQuestion(
                                new Question(question),
                                answers.subList(0, answers.size() - 1),
                                answers.get(answers.size() - 1)
                        )
                );
            }
        }

        return testQuestions;
    }
}
