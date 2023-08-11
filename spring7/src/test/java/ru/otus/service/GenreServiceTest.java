package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.GenreDto;
import ru.otus.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.Utils.assertEqualsGenreDto;
import static ru.otus.Utils.assertEqualsGenreListDto;

@DisplayName("Service to work with genre should")
@SpringBootTest
@Transactional
public class GenreServiceTest {

    private static final GenreDto NOT_EXISTING_GENRE = new GenreDto(null, "Not Exist Genre");

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
        GenreDto expected = genreService.save(NOT_EXISTING_GENRE);
        GenreDto result = genreService.getGenreById(expected.getId());
        genreService.delete(expected);

        assertEqualsGenreDto(expected, result);
    }

    @DisplayName("should correct return all genres")
    @Test
    public void shouldCorrectReturnAllGenres() {
        List<GenreDto> genres = genreService.getAll();
        assertEqualsGenreListDto(EXPECTED_GENRES, genres);
    }

    @DisplayName("should correct delete genre")
    @Test
    public void shouldCorrectDeleteGenre() {
        GenreDto genre = genreService.save(NOT_EXISTING_GENRE);

        assertDoesNotThrow(() -> genreService.delete(genre));
        assertThrows(NotFoundException.class, (() -> genreService.getGenreById(genre.getId())));
    }

    @DisplayName("should correct return genre")
    @Test
    public void shouldCorrectReturnGenre() {
        GenreDto excepted = EXPECTED_GENRES.get(0);
        GenreDto result = genreService.getGenreById(excepted.getId());

        assertEqualsGenreDto(excepted, result);
    }

    @Test
    @DisplayName("should throws NotFoundException if genre not exists")
    public void shouldThrowsNotFoundExceptionIfGenreNotExists() {
        assertThrows(NotFoundException.class, () -> genreService.getGenreById(111L));
    }
}