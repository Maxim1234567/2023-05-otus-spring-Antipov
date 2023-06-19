package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
public class StreamConfig {

    @Bean
    public PrintStream output() {
        return System.out;
    }

    @Bean
    public InputStream input() {
        return System.in;
    }
}
