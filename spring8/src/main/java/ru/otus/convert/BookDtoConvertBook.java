package ru.otus.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BookDtoConvertBook implements Converter<BookDto, Book> {
    private final AuthorDtoConvertAuthor convertAuthor;
    private final GenreDtoConvertGenre convertGenre;
    private final CommentDtoConvertComment convertComment;

    @Override
    public Book convert(BookDto bookDto) {
        Book book = Book.builder()
                .id(bookDto.getId())
                .name(bookDto.getName())
                .numberPages(bookDto.getNumberPages())
                .yearIssue(bookDto.getYearIssue())
                .build();

        List<Genre> genres = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();

        Objects.requireNonNullElse(
                bookDto.getGenres(), new ArrayList<GenreDto>()).forEach(g -> genres.add(convertGenre.convert(g)));
        Objects.requireNonNullElse(
                bookDto.getAuthors(), new ArrayList<AuthorDto>()).forEach(a -> authors.add(convertAuthor.convert(a)));
        Objects.requireNonNullElse(
                bookDto.getComments(), new ArrayList<CommentDto>()).forEach(c -> comments.add(convertComment.convert(c)));

        book.setGenres(genres);
        book.setAuthors(authors);
        book.setComments(comments);

        return book;
    }
}