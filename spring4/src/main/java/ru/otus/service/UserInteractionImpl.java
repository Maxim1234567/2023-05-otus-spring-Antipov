package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.convert.ConvertTestQuestionService;
import ru.otus.domain.Answer;
import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;
import ru.otus.domain.UserData;
import ru.otus.logging.Logging;

@Service
@RequiredArgsConstructor
public class UserInteractionImpl implements UserInteraction {
    private final ConvertTestQuestionService convert;

    private final IOService ioService;

    private final ApplicationMessageSource messageSource;


    @Override
    public UserData createUser() {
        ioService.print(messageSource.getMessage("user.name"));
        String name = ioService.readLine();

        ioService.print(messageSource.getMessage("user.lastname"));
        String lastname = ioService.readLine();

        return new UserData(name, lastname);
    }

    @Override
    @Logging
    public Result askQuestion(TestQuestion question) {
        ioService.println(convert.convert(question));
        ioService.print(messageSource.getMessage("user.answer"));

        String textAnswerUser = ioService.readLine();

        Answer correctAnswer = question.getAnswers().stream().filter(Answer::isCorrect).findFirst().get();
        Answer userAnswer = new Answer(
                textAnswerUser,
                correctAnswer.getAnswer().equals(textAnswerUser)
        );

        return new Result(
                question.getQuestion(),
                userAnswer,
                correctAnswer
        );
    }
}
