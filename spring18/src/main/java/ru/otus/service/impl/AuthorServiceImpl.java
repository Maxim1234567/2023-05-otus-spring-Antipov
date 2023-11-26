package ru.otus.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.convert.AuthorConvertAuthorDto;
import ru.otus.convert.AuthorDtoConvertAuthor;
import ru.otus.domain.Author;
import ru.otus.dto.AuthorDto;
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

    @HystrixCommand(commandKey = "GetAllAuthor")
    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return authorRepository.findAll().stream().map(convertAuthorDto::convert).toList();
    }

    @HystrixCommand(commandKey = "CreateAuthor")
    @Override
    @Transactional
    public AuthorDto create(AuthorDto author) {
        return save(author);
    }

    @HystrixCommand(commandKey = "UpdateAuthor")
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

    @HystrixCommand(commandKey = "DeleteAuthor")
    @Override
    @Transactional
    public void delete(AuthorDto author) {
        authorRepository.deleteById(author.getId());
    }

    @HystrixCommand(commandKey = "GetAuthorById")
    @Override
    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertAuthorDto.convert(author);
    }
}