package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.props.ApplicationProperties;

@Component
@RequiredArgsConstructor
public class ApplicationMessageSourceImpl implements ApplicationMessageSource {
    private final MessageSource messageSource;
    private final ApplicationProperties props;

    @Override
    public String getFileQuestion() {
        return getMessage("file-question");
    }

    @Override
    public String getTextQuestion() {
        return getMessage("question");
    }

    @Override
    public String getTextAnswerUser() {
        return getMessage("answer.user");
    }

    @Override
    public String getTextAnswerCorrect() {
        return getMessage("answer.correct");
    }

    @Override
    public String getTextUserName() {
        return getMessage("user.name");
    }

    @Override
    public String getTextUserLastname() {
        return getMessage("user.lastname");
    }

    @Override
    public String getTextUserAnswer() {
        return getMessage("user.answer");
    }

    private String getMessage(String property) {
        return messageSource.getMessage(property, new String[]{}, props.getLocale());
    }
}
