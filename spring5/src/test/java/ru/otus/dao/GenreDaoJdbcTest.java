package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao to work with genres should")
@JdbcTest
@Import(GenreDaoJdbcImpl.class)
public class GenreDaoJdbcTest {
    private static final int CORRECT_GENRE_COUNT = 10;

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
            new Genre(1000L, "Hobbies, skills")
    );

    @Autowired
    private GenreDaoJdbcImpl genreDao;

    @DisplayName("return correctly the number of genres in the database")
    @Test
    public void shouldReturnCorrectGenreCount() {
        int count = genreDao.count();
        assertEquals(CORRECT_GENRE_COUNT, count);
    }

    @DisplayName("correctly save the genre without a given ID in the database")
    @Test
    public void shouldCorrectSaveGenreWithoutId() {
        Genre genre = genreDao.insert(new Genre(null, "TEST_GENRE"));

        List<Genre> genres = genreDao.getAll();

        assertNotNull(genre.getId());
        assertTrue(genres.contains(genre));
    }

    @DisplayName("correctly updates the genre with the given ID in the database")
    @Test
    public void shouldCorrectUpdateGenreWithId() {
        List<Genre> existsGenres = genreDao.getAll();
        Genre existsGenre = existsGenres.get(existsGenres.size() - 1);
        Genre genre = new Genre(existsGenre.getId(), "TEST GENRE");
        Genre updateGenre = genreDao.update(genre);
        List<Genre> updateGenres = genreDao.getAll();

        assertFalse(existsGenres.contains(updateGenre));
        assertTrue(updateGenres.contains(updateGenre));
    }

    @DisplayName("correctly returns the expected list of genres")
    @Test
    public void shouldCorrectReturnExceptedGenreList() {
        List<Genre> genres = genreDao.getAll();

        assertEqualsList(EXPECTED_GENRES, genres);
    }

    @DisplayName("correctly return the genre by id")
    @Test
    public void shouldCorrectReturnGenreById() {
        Genre genre = genreDao.getById(EXISTING_GENRE.getId());

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

        List<Genre> excepted = List.of(genre1, genre2, genre3);

        ids.add(genre1.getId());
        ids.add(genre2.getId());
        ids.add(genre3.getId());

        List<Genre> result = genreDao.findAllById(ids);

        assertEqualsList(excepted, result);
    }

    @DisplayName("correctly return the genres by book id")
    @Test
    public void shouldCorrectGenreByBook() {
        List<Genre> excepted = List.of(EXPECTED_GENRES.get(8), EXPECTED_GENRES.get(9));

        List<Genre> result = genreDao.findGenreByBookId(100);

        assertEqualsList(excepted, result);
    }

    @DisplayName("correctly delete a genre by its id")
    @Test
    public void shouldCorrectDeleteGenreById() {
        assertThatCode(() -> genreDao.getById(EXISTING_GENRE.getId()))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXISTING_GENRE.getId());

        assertThatCode(() -> genreDao.getById(EXISTING_GENRE.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    public void assertEqualsList(List<Genre> genres1, List<Genre> genres2) {
        assertIterableEquals(
                genres1.stream().sorted(Comparator.comparing(Genre::getId)).toList(),
                genres2.stream().sorted(Comparator.comparing(Genre::getId)).toList()
        );
    }
}
