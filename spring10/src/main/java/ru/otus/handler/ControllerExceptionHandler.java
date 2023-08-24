package ru.otus.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.exception.NotFoundException;
import ru.otus.exception.ValidationErrorException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = ValidationErrorException.class)
    public ResponseEntity<String> handleValidationException(ValidationErrorException validationErrorException) {
        return ResponseEntity.badRequest().body(validationErrorException.getMessage());
    }
}