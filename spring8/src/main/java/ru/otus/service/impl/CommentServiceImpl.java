package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.convert.CommentConvertCommentDto;
import ru.otus.convert.CommentDtoConvertComment;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.CommentRepository;
import ru.otus.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentConvertCommentDto convertCommentDto;
    private final CommentDtoConvertComment convertComment;

    @Override
    @Transactional
    public CommentDto create(CommentDto comment) {
        return save(comment);
    }

    @Override
    @Transactional
    public CommentDto update(CommentDto comment) {
        return save(comment);
    }

    private CommentDto save(CommentDto comment) {
        Comment commentDomain = convertComment.convert(comment);
        Comment commentSave = commentRepository.save(commentDomain);

        return convertCommentDto.convert(commentSave);
    }

    @Override
    @Transactional
    public void delete(CommentDto comment) {
        commentRepository.deleteById(comment.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertCommentDto.convert(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId).stream().map(convertCommentDto::convert).toList();
    }
}