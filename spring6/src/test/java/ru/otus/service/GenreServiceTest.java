package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.Utils;
import ru.otus.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Service to work with genre should")
@SpringBootTest
@ActiveProfiles("genre")
public class GenreServiceTest {

    private static final Genre NOT_EXISTING_GENRE = new Genre(null, "Not Exist Genre");

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
    private GenreService genreService;

    @DisplayName("should correct save genre")
    @Test
    public void shouldCorrectSaveGenre() {
        Genre genre = genreService.save(NOT_EXISTING_GENRE);
        List<Genre> genres = genreService.getAll();
        genreService.delete(genre);

        assertTrue(genres.contains(genre));
    }

    @DisplayName("should correct return all genres")
    @Test
    public void shouldCorrectReturnAllGenres() {
        List<Genre> genres = genreService.getAll();

        Utils.assertEqualsGenreList(EXPECTED_GENRES, genres);
    }

    @DisplayName("should correct delete genre")
    @Test
    public void shouldCorrectDeleteGenre() {
        Genre genre = genreService.save(NOT_EXISTING_GENRE);
        List<Genre> genresWithNotExistsGenre = genreService.getAll();

        genreService.delete(genre);
        List<Genre> genresWithoutNotExistsGenre = genreService.getAll();

        assertTrue(genresWithNotExistsGenre.contains(genre));
        Utils.assertEqualsGenreList(EXPECTED_GENRES, genresWithoutNotExistsGenre);
    }

    @DisplayName("should correct return genre")
    @Test
    public void shouldCorrectReturnGenre() {
        Genre excepted = EXPECTED_GENRES.get(0);
        Genre result = genreService.getGenreById(excepted.getId());

        assertEquals(excepted, result);
    }
}