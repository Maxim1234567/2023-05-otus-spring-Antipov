package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.Utils;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao to work with genres should")
@JdbcTest
@Import(GenreRepositoryJdbcImpl.class)
public class GenreRepositoryJdbcTest {
    private static final Genre EXISTING_GENRE = new Genre(400L, "Tale");

    private static final List<Genre> EXPECTED_GENRES = List.of(
            new Genre(100L, "Fiction"),
            new Genre(200L, "Novel"),
            new Genre(300L, "Thriller"),
            new Genre(400L, "Tale"),
            new Genre(500L, "Comedy"),
            new Genre(600L, "Drama"),
            new Genre(700L, "Popular science literature"),
            new Genre(800L, "Art and culture"),
            new Genre(900L, "Reference books and professional literature"),
            new Genre(1000L, "Hobbies, skills"),
            new Genre(1100L, "Modern domestic prose")
    );

    @Autowired
    private GenreRepositoryJdbcImpl genreRepository;


    @DisplayName("correctly save the genre without a given ID in the database")
    @Test
    public void shouldCorrectSaveGenreWithoutId() {
        Genre genre = genreRepository.insert(new Genre(null, "TEST_GENRE"));

        List<Genre> genres = genreRepository.findAll();

        assertNotNull(genre.getId());
        assertTrue(genres.contains(genre));
    }

    @DisplayName("correctly returns the expected list of genres")
    @Test
    public void shouldCorrectReturnExceptedGenreList() {
        List<Genre> genres = genreRepository.findAll();

        Utils.assertEqualsGenreList(EXPECTED_GENRES, genres);
    }

    @DisplayName("correctly return the genres by book id")
    @Test
    public void shouldCorrectReturnGenresByBookId() {
        long bookId = 100L;
        List<Genre> expected = List.of(EXPECTED_GENRES.get(8), EXPECTED_GENRES.get(9));

        List<Genre> result = genreRepository.findByBookId(bookId);
        Utils.assertEqualsGenreList(expected, result);
    }

    @DisplayName("correctly return the genres by book ids")
    @Test
    public void shouldCorrectReturnGenresByBookIds() {
        List<Long> bookIds = List.of(100L, 200L);
        List<Genre> expected = List.of(EXPECTED_GENRES.get(8), EXPECTED_GENRES.get(9), EXPECTED_GENRES.get(5), EXPECTED_GENRES.get(6), EXPECTED_GENRES.get(1));

        List<Genre> result = genreRepository.findByBookIds(bookIds);
        Utils.assertEqualsGenreList(expected, result);
    }

    @DisplayName("correctly return the genre by id")
    @Test
    public void shouldCorrectReturnGenreById() {
        Genre genre = genreRepository.findById(EXISTING_GENRE.getId());

        assertEquals(EXISTING_GENRE, genre);
    }

    @DisplayName("correctly return the genres by ids")
    @Test
    public void shouldCorrectReturnGenresByIds() {
        List<Long> ids = new ArrayList<>();

        Random random = new Random();

        Genre genre1 = EXPECTED_GENRES.get(random.nextInt(EXPECTED_GENRES.size()));
        Genre genre2 = EXPECTED_GENRES.get(random.nextInt(EXPECTED_GENRES.size()));
        Genre genre3 = EXPECTED_GENRES.get(random.nextInt(EXPECTED_GENRES.size()));

        Set<Genre> excepted = Set.of(genre1, genre2, genre3);

        ids.add(genre1.getId());
        ids.add(genre2.getId());
        ids.add(genre3.getId());

        List<Genre> result = genreRepository.findByIds(ids);

        Utils.assertEqualsGenreList(excepted.stream().toList(), result);
    }

    @DisplayName("correctly delete a genre by its id")
    @Test
    public void shouldCorrectDeleteGenreById() {
        assertThatCode(() -> genreRepository.findById(EXISTING_GENRE.getId()))
                .doesNotThrowAnyException();

        genreRepository.deleteById(EXISTING_GENRE.getId());

        assertThatCode(() -> genreRepository.findById(EXISTING_GENRE.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("correctly return genre which used")
    @Test
    public void shouldCorrectReturnGenreWhichUsed() {
        List<Genre> excepted = List.of(
                EXPECTED_GENRES.get(1),
                EXPECTED_GENRES.get(5),
                EXPECTED_GENRES.get(6),
                EXPECTED_GENRES.get(8),
                EXPECTED_GENRES.get(9)
        );

        List<Genre> result = genreRepository.findAllUsed();

        Utils.assertEqualsGenreList(excepted, result);
    }
}
