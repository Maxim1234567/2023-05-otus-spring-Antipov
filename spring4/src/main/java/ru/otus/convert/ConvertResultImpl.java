package ru.otus.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.domain.Result;
import ru.otus.service.ApplicationMessageSource;

@Component
@RequiredArgsConstructor
public class ConvertResultImpl implements ConvertResult {
    private final ApplicationMessageSource messageSource;
    private final ConvertAnswer convertAnswer;

    @Override
    public String convert(Result result) {
        return messageSource.getMessage("question") + ": " + result.getQuestion() + "\n" +
               messageSource.getMessage("answer.user") + ": " + convertAnswer.convert(result.getUserAnswer()) + "\n" +
               messageSource.getMessage("answer.correct") + ": " + convertAnswer.convert(result.getCorrectAnswer()) + "\n" +
               "\n";
    }
}
