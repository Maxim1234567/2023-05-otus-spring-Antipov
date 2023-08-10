package ru.otus.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(Exception e) {
        super(e);
    }
}
