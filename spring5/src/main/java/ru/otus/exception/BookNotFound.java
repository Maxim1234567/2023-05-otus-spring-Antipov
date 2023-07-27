package ru.otus.exception;

public class BookNotFound extends RuntimeException {
    public BookNotFound() {
        super();
    }

    public BookNotFound(Exception e) {
        super(e);
    }
}
