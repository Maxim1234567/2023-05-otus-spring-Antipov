package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.domain.Result;
import ru.otus.domain.UserData;
import ru.otus.service.QuestionService;

import java.util.List;

@PropertySource("classpath:/application.properties")
@Configuration
@ComponentScan
public class DemoApplication {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(DemoApplication.class);

        QuestionService questionService = context.getBean(QuestionService.class);

        UserData userData = questionService.fillUserData();
        List<Result> results = questionService.askUserQuestions();

        System.out.println();
        System.out.println(userData.getFirstName() + " " + userData.getLastName());
        results.forEach(result -> {
            System.out.println("Question: " + result.getQuestion().getQuestion());
            System.out.println("Your Answer: " + result.getAnswerUser().getAnswer());
            System.out.println("Correct Answer: " + result.getCorrectAnswer().getAnswer());
            System.out.println();
        });
    }
}
