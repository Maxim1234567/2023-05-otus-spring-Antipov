package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.IOService;
import ru.otus.service.impl.IOServiceStreams;

import java.io.PrintStream;

@Configuration
public class IOServiceStreamConfig {
    @Bean
    public IOService ioService() {
        return new IOServiceStreams(new PrintStream(System.out), System.in);
    }
}
