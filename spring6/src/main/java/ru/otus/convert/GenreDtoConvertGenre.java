package ru.otus.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Genre;
import ru.otus.dto.GenreDto;

@Component
public class GenreDtoConvertGenre implements Converter<GenreDto, Genre> {
    @Override
    public Genre convert(GenreDto source) {
        return Genre.builder()
                .id(source.getId())
                .genre(source.getGenre())
                .build();
    }
}
