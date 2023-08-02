package ru.otus.service;

import ru.otus.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto save(CommentDto comment);
    void delete(CommentDto comment);
    CommentDto getCommentById(long id);
    List<CommentDto> getAllCommentsByBookId(long bookId);
}