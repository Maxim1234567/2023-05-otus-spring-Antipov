package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.convert.AuthorConvertAuthorDto;
import ru.otus.convert.AuthorDtoConvertAuthor;
import ru.otus.dto.AuthorDto;
import ru.otus.domain.Author;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorDtoConvertAuthor convertAuthor;
    private final AuthorConvertAuthorDto convertAuthorDto;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return authorRepository.findAll().stream().map(convertAuthorDto::convert).toList();
    }

    @Override
    @Transactional
    public AuthorDto create(AuthorDto author) {
        return save(author);
    }

    @Override
    @Transactional
    public AuthorDto update(AuthorDto author) {
        return save(author);
    }

    private AuthorDto save(AuthorDto author) {
        Author authorDomain = convertAuthor.convert(author);
        Author authorSave = authorRepository.save(authorDomain);

        return convertAuthorDto.convert(authorSave);
    }

    @Override
    @Transactional
    public void delete(AuthorDto author) {
        authorRepository.deleteById(author.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(String id) {
        Author author = authorRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertAuthorDto.convert(author);
    }
}