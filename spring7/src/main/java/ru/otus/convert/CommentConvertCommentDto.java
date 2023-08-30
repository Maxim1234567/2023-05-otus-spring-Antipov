package ru.otus.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Comment;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;

import java.util.Collections;
import java.util.List;

@Component
public class CommentConvertCommentDto implements Converter<Comment, CommentDto> {
    @Override
    public CommentDto convert(Comment source) {
        return CommentDto.builder()
                .id(source.getId())
                .comments(source.getComments())
                .book(
                        BookDto.builder()
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
