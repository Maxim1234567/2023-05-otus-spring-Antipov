package ru.otus.convert;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Author;
import ru.otus.dto.AuthorDto;

@Component
public class AuthorConvertAuthorDto implements Converter<Author, AuthorDto> {
    @Override
    public AuthorDto convert(Author source) {
        return AuthorDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .age(source.getAge())
                .yearBirthdate(source.getYearBirthdate())
                .build();
    }
}