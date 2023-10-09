package ru.otus.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.exception.NotFoundException;
import ru.otus.exception.ValidationErrorException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = ValidationErrorException.class)
    public ResponseEntity<String> handleValidationException(ValidationErrorException validationErrorException) {
        return ResponseEntity.badRequest().body(validationErrorException.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        exception.printStackTrace();
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}