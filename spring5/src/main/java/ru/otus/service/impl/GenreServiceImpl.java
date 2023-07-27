package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.repository.GenreRepositoryJdbc;
import ru.otus.domain.Genre;
import ru.otus.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepositoryJdbc genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public Genre save(Genre genre) {
        return genreRepository.insert(genre);
    }

    @Override
    @Transactional
    public void delete(Genre genre) {
        genreRepository.deleteById(genre.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id);
    }
}
