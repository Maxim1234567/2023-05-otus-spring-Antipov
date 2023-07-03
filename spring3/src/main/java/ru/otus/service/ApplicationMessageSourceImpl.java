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

    public String getMessage(String property) {
        return messageSource.getMessage(property, new String[]{}, props.getLocale());
    }
}
