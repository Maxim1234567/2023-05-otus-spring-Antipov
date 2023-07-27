package ru.otus.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.domain.Answer;
import ru.otus.domain.TestQuestion;

@Component
@RequiredArgsConstructor
public class ConvertTestQuestionServiceImpl implements ConvertTestQuestionService {
    private final ConvertAnswer convertAnswer;

    @Override
    public String convert(TestQuestion question) {
        StringBuilder questionAsString = new StringBuilder();

        questionAsString.append(question.getQuestion())
                .append(": \n");

        for(int i = 0; i < question.getAnswers().size(); i++) {
            Answer answer = question.getAnswers().get(i);

            if(!answer.isCorrect()) {
                questionAsString.append("\t")
                        .append(i + 1)
                        .append(". ")
                        .append(convertAnswer.convert(answer))
                        .append("\n");
            }
        }

        return questionAsString.toString();
    }
}
