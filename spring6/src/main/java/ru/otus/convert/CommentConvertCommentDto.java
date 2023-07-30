package ru.otus.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

@Component
public class CommentConvertCommentDto implements Converter<Comment, CommentDto> {
    @Override
    public CommentDto convert(Comment source) {
        return CommentDto.builder()
                .id(source.getId())
                .comments(source.getComments())
                .bookId(source.getBookId())
                .build();
    }
}
