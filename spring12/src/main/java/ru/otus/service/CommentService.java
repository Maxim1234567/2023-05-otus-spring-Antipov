package ru.otus.service;

import ru.otus.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto create(CommentDto comment);

    CommentDto update(CommentDto comment);

    void delete(CommentDto comment);

    CommentDto getCommentById(long id);

    List<CommentDto> getAllCommentsByBookId(long bookId);
}