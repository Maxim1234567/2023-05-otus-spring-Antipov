package ru.otus.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Genre;
import ru.otus.dto.GenreDto;

@Component
public class GenreConvertGenreDto implements Converter<Genre, GenreDto> {
    @Override
    public GenreDto convert(Genre source) {
        return GenreDto.builder()
                .id(source.getId())
                .genre(source.getGenre())
                .build();
    }
}
