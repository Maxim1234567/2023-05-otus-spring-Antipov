package ru.otus.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.service.ResourceProvider;

@ConfigurationProperties(prefix = "app")
@Getter
public class ApplicationProperty implements ResourceProvider {
    private final CommentProperty comment;

    public ApplicationProperty(CommentProperty comment) {
        this.comment = comment;
    }
}