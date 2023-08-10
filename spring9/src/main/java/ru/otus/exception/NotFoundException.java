package ru.otus.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {

    }

    public NotFoundException(Exception e) {
        super(e);
    }
}