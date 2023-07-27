package ru.otus.convert;

import org.springframework.stereotype.Component;
import ru.otus.domain.Answer;

@Component
public class ConvertAnswerImpl implements ConvertAnswer {
    @Override
    public String convert(Answer answer) {
        return answer.getAnswer();
    }
}
