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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class Utils {
    public static void assertEqualsGenreList(List<Genre> genres1, List<Genre> genres2) {
        List<Genre> expected = genres1.stream().sorted(Comparator.comparing(Genre::getId)).toList();
        List<Genre> result = genres2.stream().sorted(Comparator.comparing(Genre::getId)).toList();

        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++)
            assertEqualsGenre(expected.get(i), result.get(i));
    }

    public static void assertEqualsGenreListDto(List<GenreDto> genres1, List<GenreDto> genres2) {
        List<GenreDto> expected = genres1.stream().sorted(Comparator.comparing(GenreDto::getId)).toList();
        List<GenreDto> result = genres2.stream().sorted(Comparator.comparing(GenreDto::getId)).toList();

        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++)
            assertEqualsGenreDto(expected.get(i), result.get(i));
    }

    public static void assertEqualsAuthorList(List<Author> authors1, List<Author> authors2) {
        List<Author> expected = authors1.stream().sorted(Comparator.comparing(Author::getId)).toList();
        List<Author> result = authors2.stream().sorted(Comparator.comparing(Author::getId)).toList();

        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++)
            assertEqualsAuthor(expected.get(i), result.get(i));
    }

    public static void assertEqualsAuthorListDto(List<AuthorDto> authors1, List<AuthorDto> authors2) {
        List<AuthorDto> expected = authors1.stream().sorted(Comparator.comparing(AuthorDto::getId)).toList();
        List<AuthorDto> result = authors2.stream().sorted(Comparator.comparing(AuthorDto::getId)).toList();

        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++)
            assertEqualsAuthorDto(expected.get(i), result.get(i));
    }

    public static void assertEqualsCommentList(List<Comment> comments1, List<Comment> comments2) {
        List<Comment> expected = comments1.stream().sorted(Comparator.comparing(Comment::getId)).toList();
        List<Comment> result = comments2.stream().sorted(Comparator.comparing(Comment::getId)).toList();

        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++)
            assertEqualsComment(expected.get(i), result.get(i));
    }

    public static void assertEqualsCommentListDto(List<CommentDto> comments1, List<CommentDto> comments2) {
        List<CommentDto> expected = comments1.stream().sorted(Comparator.comparing(CommentDto::getId)).toList();
        List<CommentDto> result = comments2.stream().sorted(Comparator.comparing(CommentDto::getId)).toList();

        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++)
            assertEqualsCommentDto(expected.get(i), result.get(i));
    }

    public static void assertEqualsBookList(List<Book> books1, List<Book> books2) {
        List<Book> expected = books1.stream().sorted(Comparator.comparing(Book::getId)).toList();
        List<Book> result = books2.stream().sorted(Comparator.comparing(Book::getId)).toList();

        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++)
            assertEqualsBook(expected.get(i), result.get(i));
    }

    public static void assertEqualsBookListDto(List<BookDto> books1, List<BookDto> books2) {
        List<BookDto> expected = books1.stream().sorted(Comparator.comparing(BookDto::getId)).toList();
        List<BookDto> result = books2.stream().sorted(Comparator.comparing(BookDto::getId)).toList();

        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++)
            assertEqualsBookDto(expected.get(i), result.get(i));
    }

    public static void assertEqualsGenre(Genre expected, Genre result) {
        assertThat(expected).isNotNull()
                .matches(a -> a.getId().equals(result.getId()))
                .matches(a -> a.getGenre().equals(result.getGenre()));
    }

    public static void assertEqualsGenreDto(GenreDto expected, GenreDto result) {
        assertThat(expected).isNotNull()
                .matches(g -> g.getId().equals(result.getId()))
                .matches(g -> g.getGenre().equals(result.getGenre()));
    }

    public static void assertEqualsAuthor(Author expected, Author result) {
        assertThat(expected).isNotNull()
                .matches(a -> a.getId().equals(result.getId()))
                .matches(a -> a.getFirstName().equals(result.getFirstName()))
                .matches(a -> a.getLastName().equals(result.getLastName()))
                .matches(a -> a.getYearBirthdate() == result.getYearBirthdate())
                .matches(a -> a.getAge() == result.getAge());
    }

    public static void assertEqualsAuthorDto(AuthorDto expected, AuthorDto result) {
        assertThat(expected).isNotNull()
                .matches(a -> a.getId().equals(result.getId()))
                .matches(a -> a.getFirstName().equals(result.getFirstName()))
                .matches(a -> a.getLastName().equals(result.getLastName()))
                .matches(a -> a.getAge() == result.getAge())
                .matches(a -> a.getYearBirthdate() == result.getYearBirthdate());
    }

    public static void assertEqualsComment(Comment expected, Comment result) {
        assertThat(expected).isNotNull()
                .matches(c -> c.getId().equals(result.getId()))
                .matches(c -> c.getComments().equals(result.getComments()));

        assertThat(expected.getBook())
                .matches(b -> b.getId().equals(result.getBook().getId()))
                .matches(b -> b.getName().equals(result.getBook().getName()))
                .matches(b -> b.getYearIssue().equals(result.getBook().getYearIssue()))
                .matches(b -> b.getNumberPages().equals(result.getBook().getNumberPages()));
    }

    public static void assertEqualsCommentDto(CommentDto expected, CommentDto result) {
        assertThat(expected).isNotNull()
                .matches(c -> c.getId().equals(result.getId()))
                .matches(c -> c.getComments().equals(result.getComments()));


        assertThat(expected.getBook())
                .matches(b -> b.getId().equals(result.getBook().getId()))
                .matches(b -> b.getName().equals(result.getBook().getName()))
                .matches(b -> b.getYearIssue().equals(result.getBook().getYearIssue()))
                .matches(b -> b.getNumberPages().equals(result.getBook().getNumberPages()));
    }

    public static void assertEqualsBook(Book expected, Book result) {
        assertThat(expected).isNotNull()
                .matches(b -> b.getId().equals(result.getId()))
                .matches(b -> b.getName().equals(result.getName()))
                .matches(b -> b.getNumberPages().equals(result.getNumberPages()))
                .matches(b -> b.getYearIssue().equals(result.getYearIssue()));

        assertEqualsGenreList(expected.getGenres(), result.getGenres());
        assertEqualsAuthorList(expected.getAuthors(), result.getAuthors());
        assertEqualsCommentList(expected.getComments(), result.getComments());
    }

    public static void assertEqualsBookDto(BookDto expected, BookDto result) {
        assertThat(expected).isNotNull()
                .matches(b -> b.getId().equals(result.getId()))
                .matches(b -> b.getName().equals(result.getName()))
                .matches(b -> b.getNumberPages().equals(result.getNumberPages()))
                .matches(b -> b.getYearIssue().equals(result.getYearIssue()));

        assertEqualsGenreListDto(expected.getGenres(), result.getGenres());
        assertEqualsAuthorListDto(expected.getAuthors(), result.getAuthors());
        assertEqualsCommentListDto(expected.getComments(), result.getComments());
    }
}