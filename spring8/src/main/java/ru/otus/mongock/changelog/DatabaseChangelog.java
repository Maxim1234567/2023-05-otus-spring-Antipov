package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {
    private final List<Genre> genres = List.of(
            Genre.builder().id("100").genre("Fiction").build(),
            Genre.builder().id("200").genre("Novel").build(),
            Genre.builder().id("300").genre("Thriller").build(),
            Genre.builder().id("400").genre("Tale").build(),
            Genre.builder().id("500").genre("Comedy").build(),
            Genre.builder().id("600").genre("Drama").build(),
            Genre.builder().id("700").genre("Popular science literature").build(),
            Genre.builder().id("800").genre("Art and culture").build(),
            Genre.builder().id("900").genre("Reference books and professional literature").build(),
            Genre.builder().id("1000").genre("Hobbies, skills").build(),
            Genre.builder().id("1100").genre("Modern domestic prose").build()
    );

    private final List<Author> authors = List.of(
            Author.builder().id("100").firstName("Herbert").lastName("Shieldt").age(72).yearBirthdate(1951).build(),
            Author.builder().id("200").firstName("Ivan").lastName("Efremov").age(64).yearBirthdate(1908).build(),
            Author.builder().id("300").firstName("Isaac").lastName("Asimov").age(72).yearBirthdate(1919).build(),
            Author.builder().id("400").firstName("Irvine").lastName("Welsh").age(64).yearBirthdate(1958).build(),
            Author.builder().id("500").firstName("Lyubov").lastName("Voronkova").age(70).yearBirthdate(1906).build()
    );

    private final List<Comment> comments = List.of(
            Comment.builder().id("100").comments("Good Book!").bookId("100").build(),
            Comment.builder().id("200").comments("Very Interesting!").bookId("100").build(),
            Comment.builder().id("300").comments("I cried when I read it").bookId("100").build(),
            Comment.builder().id("400").comments("Isaac Asimov Top").bookId("300").build(),
            Comment.builder().id("500").comments("The best book in the world").bookId("300").build(),
            Comment.builder().id("600").comments("I read it, it's cool").bookId("200").build()
    );

    private final List<Book> books = List.of(
            Book.builder().id("100").name("Java. Complete guide").yearIssue(2022).numberPages(1344)
                    .genres(List.of(genres.get(8), genres.get(9)))
                    .authors(List.of(authors.get(0)))
                    .comments(List.of(comments.get(0), comments.get(1), comments.get(2)))
                    .build(),
            Book.builder().id("200").name("Starships. Andromeda's nebula").yearIssue(1987).numberPages(400)
                    .genres(List.of(genres.get(5), genres.get(6), genres.get(1)))
                    .authors(List.of(authors.get(1)))
                    .comments(List.of(comments.get(5)))
                    .build(),
            Book.builder().id("300").name("FOUNDATION").yearIssue(2022).numberPages(320)
                    .genres(List.of(genres.get(5), genres.get(6), genres.get(1)))
                    .authors(List.of(authors.get(2)))
                    .comments(List.of(comments.get(3), comments.get(4)))
                    .build(),
            Book.builder().id("400").name("Alice's Adventures in Wonderland").yearIssue(1865).numberPages(225)
                    .build()
    );

    @ChangeSet(order = "001", id = "dropDb", author = "antipov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "antipov", runAlways = true)
    public void insertGenres(GenreRepository repository) {
        repository.saveAll(genres);
    }

    @ChangeSet(order = "003", id = "insertAuthor", author = "antipov", runAlways = true)
    public void insertAuthor(AuthorRepository repository) {
        repository.saveAll(authors);
    }

    @ChangeSet(order = "004", id = "insertBook", author = "antipov", runAlways = true)
    public void insertBook(BookRepository repository) {
        repository.saveAll(books);
    }

    @ChangeSet(order = "005", id = "insertComment", author = "antipov", runAlways = true)
    public void insertComment(CommentRepository repository) {
        repository.saveAll(comments);
    }
}
