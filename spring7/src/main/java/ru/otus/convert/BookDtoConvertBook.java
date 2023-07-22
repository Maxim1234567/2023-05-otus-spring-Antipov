package ru.otus.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDtoConvertBook implements Converter<BookDto, Book> {
    private final AuthorDtoConvertAuthor convertAuthor;
    private final GenreDtoConvertGenre convertGenre;

    @Override
    public Book convert(BookDto source) {
        Book book = Book.builder()
                .id(source.getId())
                .name(source.getName())
                .numberPages(source.getNumberPages())
                .yearIssue(source.getYearIssue())
                .build();

        List<Genre> genres = new ArrayList<>();
        List<Author> authors = new ArrayList<>();

        source.getGenres().forEach(g -> genres.add(convertGenre.convert(g)));
        source.getAuthors().forEach(a -> authors.add(convertAuthor.convert(a)));

        book.setGenres(genres);
        book.setAuthors(authors);

        return book;
    }
}