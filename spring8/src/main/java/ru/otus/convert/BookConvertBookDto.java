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
public class BookConvertBookDto implements Converter<Book, BookDto> {
    private final AuthorConvertAuthorDto convertAuthor;
    private final GenreConvertGenreDto convertGenre;
    private final CommentConvertCommentDto convertComment;


    @Override
    public BookDto convert(Book book) {
        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .numberPages(book.getNumberPages())
                .yearIssue(book.getYearIssue())
                .build();

        List<GenreDto> genres = new ArrayList<>();
        List<AuthorDto> authors = new ArrayList<>();
        List<CommentDto> comments = new ArrayList<>();

        Objects.requireNonNullElse(
                book.getGenres(), new ArrayList<Genre>()).forEach(g -> genres.add(convertGenre.convert(g)));
        Objects.requireNonNullElse(
                book.getAuthors(), new ArrayList<Author>()).forEach(a -> authors.add(convertAuthor.convert(a)));
        Objects.requireNonNullElse(
                book.getComments(), new ArrayList<Comment>()).forEach(c -> comments.add(convertComment.convert(c)));

        bookDto.setGenres(genres);
        bookDto.setAuthors(authors);
        bookDto.setComments(comments);

        return bookDto;
    }
}