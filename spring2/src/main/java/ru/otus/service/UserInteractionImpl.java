package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;

@Service
@RequiredArgsConstructor
public class UserInteractionImpl implements UserInteraction {
    private final ConvertTestQuestionService convert;

    private final IOService ioService;

    @Override
    public String askFirstName() {
        ioService.print("Enter your First Name: ");

        return ioService.readLine();
    }

    @Override
    public String askLastName() {
        ioService.print("Enter your Last Name: ");

        return ioService.readLine();
    }

    @Override
    public Result askQuestion(TestQuestion question) {
        ioService.println(convert.convert(question));
        ioService.print("Enter your answer: ");

        return new Result(
                question.getQuestion(),
                ioService.readLine(),
                question.getCorrectAnswer()
        );
    }
}
