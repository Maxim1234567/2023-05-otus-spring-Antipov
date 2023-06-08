package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Answer;
import ru.otus.domain.Result;
import ru.otus.domain.TestQuestion;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class UserInteractionImpl implements UserInteraction {
    private final ConvertTestQuestionService convert;

    @Override
    public String askFirstName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your First Name: ");

        return scanner.nextLine();
    }

    @Override
    public String askLastName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Last Name: ");

        return scanner.nextLine();
    }

    @Override
    public Result askQuestion(TestQuestion question) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(convert.convert(question));
        System.out.print("Enter your answer: ");

        return new Result(
                question.getQuestion(),
                new Answer(scanner.nextLine()),
                question.getCorrectAnswer()
        );
    }
}
