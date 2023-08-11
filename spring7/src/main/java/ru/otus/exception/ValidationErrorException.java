package ru.otus.exception;

public class ValidationErrorException extends RuntimeException {
    public ValidationErrorException(String msg) {
        super(msg);
    }
}
