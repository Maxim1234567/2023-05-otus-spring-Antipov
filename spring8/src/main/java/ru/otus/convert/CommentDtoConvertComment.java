package ru.otus.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

@Component
public class CommentDtoConvertComment implements Converter<CommentDto, Comment> {
    @Override
    public Comment convert(CommentDto source) {
        return Comment.builder()
                .id(source.getId())
                .comments(source.getComments())
                .bookId(source.getBookId())
                .build();
    }
}