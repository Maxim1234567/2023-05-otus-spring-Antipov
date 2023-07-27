package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMessageSourceImpl implements ApplicationMessageSource {
    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public String getMessage(String property) {
        return messageSource.getMessage(property, new String[]{}, localeProvider.getLocale());
    }
}
