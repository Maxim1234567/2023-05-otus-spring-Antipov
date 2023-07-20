package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.Utils;
import ru.otus.domain.Genre;
import ru.otus.dto.GenreDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Service to work with genre should")
@SpringBootTest
@ActiveProfiles("genre")
public class GenreServiceTest {

    private static final GenreDto NOT_EXISTING_GENRE = new GenreDto(null, "Not Exist Genre");

    private static final GenreDto EXISTING_GENRE = new GenreDto(400L, "Tale");

    private static final List<GenreDto> EXPECTED_GENRES = List.of(
            new GenreDto(100L, "Fiction"),
            new GenreDto(200L, "Novel"),
            new GenreDto(300L, "Thriller"),
            new GenreDto(400L, "Tale"),
            new GenreDto(500L, "Comedy"),
            new GenreDto(600L, "Drama"),
            new GenreDto(700L, "Popular science literature"),
            new GenreDto(800L, "Art and culture"),
            new GenreDto(900L, "Reference books and professional literature"),
            new GenreDto(1000L, "Hobbies, skills"),
            new GenreDto(1100L, "Modern domestic prose")
    );

    @Autowired
    private GenreService genreService;

    @DisplayName("should correct save genre")
    @Test
    public void shouldCorrectSaveGenre() {
        GenreDto genre = genreService.save(NOT_EXISTING_GENRE);
        List<GenreDto> genres = genreService.getAll();
        genreService.delete(genre);

        assertTrue(genres.contains(genre));
    }

    @DisplayName("should correct return all genres")
    @Test
    public void shouldCorrectReturnAllGenres() {
        List<GenreDto> genres = genreService.getAll();

        Utils.assertEqualsGenreListDto(EXPECTED_GENRES, genres);
    }

    @DisplayName("should correct delete genre")
    @Test
    public void shouldCorrectDeleteGenre() {
        GenreDto genre = genreService.save(NOT_EXISTING_GENRE);
        List<GenreDto> genresWithNotExistsGenre = genreService.getAll();

        genreService.delete(genre);
        List<GenreDto> genresWithoutNotExistsGenre = genreService.getAll();

        assertTrue(genresWithNotExistsGenre.contains(genre));
        Utils.assertEqualsGenreListDto(EXPECTED_GENRES, genresWithoutNotExistsGenre);
    }

    @DisplayName("should correct return genre")
    @Test
    public void shouldCorrectReturnGenre() {
        GenreDto excepted = EXPECTED_GENRES.get(0);
        GenreDto result = genreService.getGenreById(excepted.getId());

        assertEquals(excepted, result);
    }

    @Test
    public void shouldCorrectReturnEmptyGenreDtoIfAuthorNotExists() {
        assertTrue(false);
    }
}