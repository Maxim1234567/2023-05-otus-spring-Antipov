package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.convert.GenreConvertGenreDto;
import ru.otus.convert.GenreDtoConvertGenre;
import ru.otus.domain.Genre;
import ru.otus.dto.GenreDto;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.GenreRepository;
import ru.otus.service.impl.GenreServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.otus.Utils.assertEqualsGenre;
import static ru.otus.Utils.assertEqualsGenreDto;
import static ru.otus.Utils.assertEqualsGenreListDto;

@DisplayName("Service to work with genre should")
@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    GenreDto genreDto = new GenreDto(100L, "Fiction");
    Genre genre = new Genre(100L, "Fiction");

    private static final List<GenreDto> EXPECTED_GENRES_DTO = List.of(
            new GenreDto(100L, "Fiction"),
            new GenreDto(200L, "Novel"),
            new GenreDto(300L, "Thriller")
    );

    private static final List<Genre> EXPECTED_GENRES = List.of(
            new Genre(100L, "Fiction"),
            new Genre(200L, "Novel"),
            new Genre(300L, "Thriller")
    );

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreDtoConvertGenre convertGenre;

    @Mock
    private GenreConvertGenreDto convertGenreDto;

    private GenreService genreService;

    @BeforeEach
    public void setUp() {
        genreService = new GenreServiceImpl(
                genreRepository,
                convertGenre,
                convertGenreDto
        );
    }

    @DisplayName("should correct save genre")
    @Test
    public void shouldCorrectSaveGenre() {
        given(convertGenre.convert(eq(genreDto)))
                .willReturn(genre);
        given(genreRepository.save(eq(genre)))
                .willReturn(genre);
        given(convertGenreDto.convert(eq(genre)))
                .willReturn(genreDto);

        GenreDto result = genreService.create(genreDto);

        assertEqualsGenreDto(result, genreDto);

        verify(convertGenre, times(1))
                .convert(eq(genreDto));
        verify(genreRepository, times(1))
                .save(eq(genre));
        verify(convertGenreDto, times(1))
                .convert(eq(genre));
    }

    @DisplayName("should correct return all genres")
    @Test
    public void shouldCorrectReturnAllGenres() {
        given(genreRepository.findAll())
                .willReturn(EXPECTED_GENRES);
        given(convertGenreDto.convert(EXPECTED_GENRES.get(0)))
                .willReturn(EXPECTED_GENRES_DTO.get(0));
        given(convertGenreDto.convert(EXPECTED_GENRES.get(1)))
                .willReturn(EXPECTED_GENRES_DTO.get(1));
        given(convertGenreDto.convert(EXPECTED_GENRES.get(2)))
                .willReturn(EXPECTED_GENRES_DTO.get(2));

        List<GenreDto> results = genreService.getAll();

        assertEqualsGenreListDto(results, EXPECTED_GENRES_DTO);

        verify(genreRepository, times(1))
                .findAll();
        verify(convertGenreDto, times(1))
                .convert(eq(EXPECTED_GENRES.get(0)));
        verify(convertGenreDto, times(1))
                .convert(eq(EXPECTED_GENRES.get(1)));
        verify(convertGenreDto, times(1))
                .convert(eq(EXPECTED_GENRES.get(2)));
    }

    @DisplayName("should correct delete genre")
    @Test
    public void shouldCorrectDeleteGenre() {
        genreService.delete(genreDto);

        verify(genreRepository, times(1))
                .deleteById(eq(genreDto.getId()));
    }

    @DisplayName("should correct return genre")
    @Test
    public void shouldCorrectReturnGenre() {
        given(genreRepository.findById(eq(genreDto.getId())))
                .willReturn(Optional.of(genre));
        given(convertGenreDto.convert(genre))
                .willReturn(genreDto);

        GenreDto results = genreService.getGenreById(genreDto.getId());

        assertEqualsGenreDto(results, genreDto);

        verify(genreRepository, times(1))
                .findById(eq(genreDto.getId()));
        verify(convertGenreDto, times(1))
                .convert(eq(genre));
    }

    @Test
    @DisplayName("should throws NotFoundException if genre not exists")
    public void shouldThrowsNotFoundExceptionIfGenreNotExists() {
        given(genreRepository.findById(eq(genreDto.getId())))
                .willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> genreService.getGenreById(genreDto.getId()));

        verify(genreRepository, times(1))
                .findById(eq(genreDto.getId()));
        verify(convertGenreDto, times(0))
                .convert(any(Genre.class));
    }
}