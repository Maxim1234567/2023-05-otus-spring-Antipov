package ru.otus.props;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.service.LocaleProvider;
import ru.otus.service.ResourceProvider;
import ru.otus.service.SettingsProvider;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
@Getter
public class ApplicationProperties implements LocaleProvider, ResourceProvider, SettingsProvider {
    private final String delimiter;

    private final Locale locale;

    private final String fileName;

    public ApplicationProperties(String delimiter, Locale locale, String fileName) {
        this.delimiter = delimiter;
        this.locale = locale;
        this.fileName = fileName;
    }
}
