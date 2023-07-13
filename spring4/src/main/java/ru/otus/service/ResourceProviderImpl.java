package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.props.ApplicationProperties;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ResourceProviderImpl implements ResourceProvider {
    private final ApplicationProperties applicationProperties;

    @Override
    public String getFileName() {
        return applicationProperties.getFileName();
    }

    @Override
    public String getDelimiter() {
        return applicationProperties.getDelimiter();
    }

    @Override
    public Locale getLocale() {
        return applicationProperties.getLocale();
    }
}
