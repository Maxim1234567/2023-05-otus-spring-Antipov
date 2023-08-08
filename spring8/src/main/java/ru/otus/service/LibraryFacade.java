package ru.otus.service;

public interface LibraryFacade {
    void createBook();

    void createAuthor();

    void createGenre();

    void createComment(String bookId);

    void updateBook();

    void showBook();

    void showBooks();

    void showAuthor();

    void showAuthors();

    void showGenre();

    void showGenres();

    void showCommentsByBook(String bookId);

    void deleteBook();
}