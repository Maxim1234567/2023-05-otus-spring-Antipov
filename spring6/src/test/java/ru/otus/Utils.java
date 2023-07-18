package ru.otus;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

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
}