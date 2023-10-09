package ru.otus.service;

import ru.otus.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();

    AuthorDto create(AuthorDto author);

    AuthorDto update(AuthorDto author);

    void delete(AuthorDto author);

    AuthorDto getAuthorById(Long id);
}