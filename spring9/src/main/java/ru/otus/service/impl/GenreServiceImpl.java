package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.convert.GenreConvertGenreDto;
import ru.otus.convert.GenreDtoConvertGenre;
import ru.otus.dto.GenreDto;
import ru.otus.domain.Genre;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.GenreRepository;
import ru.otus.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreDtoConvertGenre convertGenre;
    private final GenreConvertGenreDto convertGenreDto;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAll() {
        return genreRepository.findAll().stream().map(convertGenreDto::convert).toList();
    }

    @Override
    @Transactional
    public GenreDto create(GenreDto genre) {
        return save(genre);
    }

    @Override
    public GenreDto update(GenreDto genre) {
        return save(genre);
    }

    public GenreDto save(GenreDto genre) {
        Genre genreDomain = convertGenre.convert(genre);
        Genre genreSave = genreRepository.save(genreDomain);

        return convertGenreDto.convert(genreSave);
    }

    @Override
    @Transactional
    public void delete(GenreDto genre) {
        genreRepository.deleteById(genre.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertGenreDto.convert(genre);
    }
}