package ru.otus.exception;

public class GenreNotFound extends RuntimeException {
    public GenreNotFound() {
        super();
    }

    public GenreNotFound(Exception e) {
        super(e);
    }
}
