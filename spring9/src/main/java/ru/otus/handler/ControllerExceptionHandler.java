package ru.otus.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.exception.NotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    public String handleNotFoundException() {
        return "not-found-exception";
    }
}
