package ru.otus.exception;

/*
    public GenreNotFound() {
        super();
    }

    public GenreNotFound(Exception e) {
        super(e);
    }
*/

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(Exception e) {
        super(e);
    }
}
