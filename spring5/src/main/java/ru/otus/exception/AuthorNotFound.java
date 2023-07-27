package ru.otus.exception;

public class AuthorNotFound extends RuntimeException {
    public AuthorNotFound() {
        super();
    }

    public AuthorNotFound(Exception e) {
        super(e);
    }
}
