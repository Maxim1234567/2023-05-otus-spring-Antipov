package ru.otus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.CommentDto;
import ru.otus.exception.ValidationErrorException;
import ru.otus.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment")
    public CommentDto addComment(@Valid @RequestBody CommentDto comment, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationErrorException("Validation Error");
        }

        return commentService.create(comment);
    }
}
