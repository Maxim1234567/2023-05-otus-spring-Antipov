package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;
import ru.otus.logging.Logging;

@Service
@RequiredArgsConstructor
public class UserInteractionImpl implements UserInteraction {
    private final ConvertTestQuestionService convert;

    private final IOService ioService;

    private final ApplicationMessageSource messageSource;

    @Override
    @Logging
    public String askFirstName() {
        ioService.print(messageSource.getMessage("user.name"));

        return ioService.readLine();
    }

    @Override
    @Logging
    public String askLastName() {
        ioService.print(messageSource.getMessage("user.lastname"));

        return ioService.readLine();
    }

    @Override
    @Logging
    public Result askQuestion(TestQuestion question) {
        ioService.println(convert.convert(question));
        ioService.print(messageSource.getMessage("user.answer"));

        return new Result(
                question.getQuestion(),
                ioService.readLine(),
                question.getCorrectAnswer()
        );
    }
}
