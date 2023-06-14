package ru.otus.service;


import org.springframework.stereotype.Component;
import ru.otus.domain.TestQuestion;

@Component
public class ConvertTestQuestionServiceImpl implements ConvertTestQuestionService {
    @Override
    public String convert(TestQuestion question) {
        StringBuilder questionAsString = new StringBuilder();
        
        questionAsString.append(question.getQuestion())
                .append(": \n");
        
        for(int i = 0; i < question.getAnswers().size(); i++) {
            questionAsString.append("\t")
                    .append(i + 1)
                    .append(". ")
                    .append(question.getAnswers().get(i))
                    .append("\n");
        }
        
        return questionAsString.toString();
    }
}
