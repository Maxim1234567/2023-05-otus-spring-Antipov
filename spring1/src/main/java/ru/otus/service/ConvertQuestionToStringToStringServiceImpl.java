package ru.otus.service;


import ru.otus.domain.Question;

public class ConvertQuestionToStringToStringServiceImpl implements ConvertQuestionToStringService {
    @Override
    public String questionToString(Question question) {
        StringBuilder questionAsString = new StringBuilder();
        
        questionAsString.append(question.getQuestion())
                .append(": \n");
        
        for(int i = 0; i < question.getAnswers().size(); i++) {
            questionAsString.append("\t")
                    .append(i + 1)
                    .append(". ")
                    .append(question.getAnswers().get(i).getAnswer())
                    .append("\n");
        }
        
        return questionAsString.toString();
    }
}
