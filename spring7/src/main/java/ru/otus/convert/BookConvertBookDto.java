package ru.otus.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookConvertBookDto implements Converter<Book, BookDto> {
    private final AuthorConvertAuthorDto convertAuthor;
    private final GenreConvertGenreDto convertGenre;


    @Override
    public BookDto convert(Book source) {
        BookDto bookDto = BookDto.builder()
                .id(source.getId())
                .name(source.getName())
                .numberPages(source.getNumberPages())
                .yearIssue(source.getYearIssue())
                .build();

        List<GenreDto> genres = new ArrayList<>();
        List<AuthorDto> authors = new ArrayList<>();

        source.getGenres().forEach(g -> genres.add(convertGenre.convert(g)));
        source.getAuthors().forEach(a -> authors.add(convertAuthor.convert(a)));

        bookDto.setGenres(genres);
        bookDto.setAuthors(authors);

        return bookDto;
    }
}