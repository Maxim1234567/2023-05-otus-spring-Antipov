package ru.otus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final BookService bookService;

    private final CommentService commentService;

    @GetMapping("/comment/create")
    public String createPage(@RequestParam("id") long bookId, Model model) {
        BookDto book = bookService.getBookById(bookId);

        CommentDto comment = CommentDto.builder()
                .book(book)
                .build();

        model.addAttribute("comment", comment);

        return "create-comment";
    }

    @PostMapping("/comment/create")
    public String createPage(@Valid @ModelAttribute("comment") CommentDto comment,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create-comment";
        }

        BookDto book = bookService.getBookById(comment.getBook().getId());
        comment.setBook(book);
        commentService.create(comment);

        return "redirect:/";
    }
}
