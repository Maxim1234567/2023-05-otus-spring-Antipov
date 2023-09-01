package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.domain.Genre;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.Utils.assertEqualsGenre;
import static ru.otus.Utils.assertEqualsGenreList;

@DisplayName("Dao to work with genres should")
@DataMongoTest
public class GenreRepositoryTest {
    private static final Genre EXISTING_GENRE = new Genre("400", "Tale");

    private static final List<Genre> EXPECTED_GENRES = List.of(
            new Genre("100", "Fiction"),
            new Genre("200", "Novel"),
            new Genre("300", "Thriller"),
            new Genre("400", "Tale"),
            new Genre("500", "Comedy"),
            new Genre("600", "Drama"),
            new Genre("700", "Popular science literature"),
            new Genre("800", "Art and culture"),
            new Genre("900", "Reference books and professional literature"),
            new Genre("1000", "Hobbies, skills"),
            new Genre("1100", "Modern domestic prose")
    );

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("correctly save the genre without a given ID in the database")
    @Test
    public void shouldCorrectSaveGenreWithoutId() {
        Genre genre = genreRepository.save(new Genre(null, "TEST_GENRE"));

        assertThat(genreRepository.findById(genre.getId()))
                .isNotNull();

        genreRepository.delete(genre);

        assertNotNull(genre.getId());
    }

    @DisplayName("correctly returns the expected list of genres")
    @Test
    public void shouldCorrectReturnExceptedGenreList() {
        List<Genre> genres = genreRepository.findAll();

        assertEqualsGenreList(EXPECTED_GENRES, genres);
    }

    @DisplayName("correctly return the genre by id")
    @Test
    public void shouldCorrectReturnGenreById() {
        Genre genre = genreRepository.findById(EXISTING_GENRE.getId()).get();

        assertEqualsGenre(EXISTING_GENRE, genre);
    }

    @DisplayName("correctly return the genres by ids")
    @Test
    public void shouldCorrectReturnGenresByIds() {
        List<String> ids = new ArrayList<>();

        Random random = new Random();

        Genre genre1 = EXPECTED_GENRES.get(random.nextInt(EXPECTED_GENRES.size()));
        Genre genre2 = EXPECTED_GENRES.get(random.nextInt(EXPECTED_GENRES.size()));
        Genre genre3 = EXPECTED_GENRES.get(random.nextInt(EXPECTED_GENRES.size()));

        Set<Genre> excepted = new HashSet<>();
        excepted.add(genre1);
        excepted.add(genre2);
        excepted.add(genre3);

        ids.add(genre1.getId());
        ids.add(genre2.getId());
        ids.add(genre3.getId());

        List<Genre> result = genreRepository.findByIds(ids);

        assertEqualsGenreList(excepted.stream().toList(), result);
    }

    @DisplayName("correctly delete a genre by its id")
    @Test
    public void shouldCorrectDeleteGenreById() {
        assertThat(genreRepository.findById(EXISTING_GENRE.getId()))
                .isNotNull();

        genreRepository.deleteById(EXISTING_GENRE.getId());

        assertThat(genreRepository.findById(EXISTING_GENRE.getId()))
                .isEqualTo(Optional.empty());

        genreRepository.save(EXISTING_GENRE);
    }

    @Test
    public void shouldCorrectReturnEmptyOptionalIfGenreNotExists() {
        assertThat(genreRepository.findById("111"))
                .isEqualTo(Optional.empty());
    }
}