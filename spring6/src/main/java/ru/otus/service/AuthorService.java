package ru.otus.service;

import ru.otus.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();

    AuthorDto save(AuthorDto author);

    void delete(AuthorDto author);

    AuthorDto getAuthorById(Long id);
}