package ru.otus;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class Utils {
    public static void assertEqualsGenreList(List<Genre> genres1, List<Genre> genres2) {
        assertIterableEquals(
                genres1.stream().sorted(Comparator.comparing(Genre::getId)).toList(),
                genres2.stream().sorted(Comparator.comparing(Genre::getId)).toList()
        );
    }

    public static void assertEqualsBookList(List<Book> books1, List<Book> books2) {
        assertIterableEquals(
                books1.stream().sorted(Comparator.comparing(Book::getId)).toList(),
                books2.stream().sorted(Comparator.comparing(Book::getId)).toList()
        );
    }

    public static void assertEqualsAuthorList(List<Author> authors1, List<Author> authors2) {
        assertIterableEquals(
                authors1.stream().sorted(Comparator.comparing(Author::getId)).toList(),
                authors2.stream().sorted(Comparator.comparing(Author::getId)).toList()
        );
    }

    public static void assertEqualsCommentList(List<Comment> comments1, List<Comment> comments2) {
        assertIterableEquals(
                comments1.stream().sorted(Comparator.comparing(Comment::getId)).toList(),
                comments2.stream().sorted(Comparator.comparing(Comment::getId)).toList()
        );
    }

    public static void assertEqualsCommentListDto(List<CommentDto> comments1, List<CommentDto> comments2) {
        assertIterableEquals(
                comments1.stream().sorted(Comparator.comparing(CommentDto::getId)).toList(),
                comments2.stream().sorted(Comparator.comparing(CommentDto::getId)).toList()
        );
    }

    public static void assertEqualsGenreListDto(List<GenreDto> genres1, List<GenreDto> genres2) {
        assertIterableEquals(
                genres1.stream().sorted(Comparator.comparing(GenreDto::getId)).toList(),
                genres2.stream().sorted(Comparator.comparing(GenreDto::getId)).toList()
        );
    }

    public static void assertEqualsBookListDto(List<BookDto> books1, List<BookDto> books2) {
        assertIterableEquals(
                books1.stream().sorted(Comparator.comparing(BookDto::getId)).toList(),
                books2.stream().sorted(Comparator.comparing(BookDto::getId)).toList()
        );
    }

    public static void assertEqualsAuthorListDto(List<AuthorDto> authors1, List<AuthorDto> authors2) {
        assertIterableEquals(
                authors1.stream().sorted(Comparator.comparing(AuthorDto::getId)).toList(),
                authors2.stream().sorted(Comparator.comparing(AuthorDto::getId)).toList()
        );
    }
}