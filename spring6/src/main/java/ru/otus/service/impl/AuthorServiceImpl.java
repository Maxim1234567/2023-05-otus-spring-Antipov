package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.repository.AuthorRepositoryJdbc;
import ru.otus.domain.Author;
import ru.otus.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepositoryJdbc authorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public Author save(Author author) {
        return authorRepository.insert(author);
    }

    @Override
    @Transactional
    public void delete(Author author) {
        authorRepository.deleteById(author.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id);
    }
}