package ru.otus.props;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
@Getter
public class ApplicationProperties {
    private final String delimiter;

    private final Locale locale;

    public ApplicationProperties(String delimiter, Locale locale) {
        this.delimiter = delimiter;
        this.locale = locale;
    }
}
