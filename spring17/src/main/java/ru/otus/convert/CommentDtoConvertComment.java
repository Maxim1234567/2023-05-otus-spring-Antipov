package ru.otus.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

import java.util.Collections;

@Component
public class CommentDtoConvertComment implements Converter<CommentDto, Comment> {
    @Override
    public Comment convert(CommentDto source) {
        return Comment.builder()
                .id(source.getId())
                .comments(source.getComments())
                .book(
                        Book.builder()
                                .id(source.getBook().getId())
                                .name(source.getBook().getName())
                                .numberPages(source.getBook().getNumberPages())
                                .yearIssue(source.getBook().getYearIssue())
                                .comments(Collections.emptyList())
                                .authors(Collections.emptyList())
                                .genres(Collections.emptyList())
                                .build()
                )
                .build();
    }
}