package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.CommentDto;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final BookService bookService;

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public CommentDto addComment(@RequestBody CommentDto comment) {
        return commentService.create(comment);
    }
}
