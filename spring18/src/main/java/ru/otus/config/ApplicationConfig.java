package ru.otus.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.otus.property.ApplicationProperty;

@Configuration
@EnableConfigurationProperties(ApplicationProperty.class)
public class ApplicationConfig {
}