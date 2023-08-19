package ru.otus.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

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

        bookDto.getGenres().forEach(g -> genres.add(convertGenre.convert(g)));
        bookDto.getAuthors().forEach(a -> authors.add(convertAuthor.convert(a)));
        bookDto.getComments().forEach(c -> comments.add(convertComment.convert(c)));

        book.setGenres(genres);
        book.setAuthors(authors);
        book.setComments(comments);

        return book;
    }
}