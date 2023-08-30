package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.CommentDto;
import ru.otus.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.Utils.assertEqualsCommentDto;
import static ru.otus.Utils.assertEqualsCommentListDto;

@DisplayName("Service to work with comment should")
@SpringBootTest
@Transactional
public class CommentServiceTest {
    private static final CommentDto EXISTING_COMMENT = new CommentDto(
            100L, "Good Book!", 100L
    );

    private static final List<CommentDto> EXPECTED_COMMENTS_BY_BOOK_ID = List.of(
            new CommentDto(100L, "Good Book!", 100L),
            new CommentDto(200L, "Very Interesting!", 100L),
            new CommentDto(300L, "I cried when I read it", 100L)
    );

    private static final CommentDto NOT_EXISTS_COMMENT = new CommentDto(
            null, "Read the book many times", 200L
    );

    @Autowired
    private CommentService commentService;

    @DisplayName(" should correct save comment")
    @Test
    public void shouldCorrectSaveComment() {
        CommentDto comment = commentService.save(NOT_EXISTS_COMMENT);
        List<CommentDto> comments = commentService.getAllCommentsByBookId(200L);
        commentService.delete(comment);

        assertEqualsCommentDto(comment, comments.stream().filter(c -> c.getId().equals(comment.getId())).findFirst().get());
    }

    @DisplayName(" should correct return all comments by book id")
    @Test
    public void shouldCorrectReturnAllCommentsByBookId() {
        List<CommentDto> comments = commentService.getAllCommentsByBookId(100L);

        assertEqualsCommentListDto(EXPECTED_COMMENTS_BY_BOOK_ID, comments);
    }

    @DisplayName(" should correct return comment by id")
    @Test
    public void shouldCorrectReturnComment() {
        CommentDto comment = commentService.getCommentById(EXISTING_COMMENT.getId());
        assertEqualsCommentDto(EXISTING_COMMENT, comment);
    }

    @DisplayName(" should correct delete comment")
    @Test
    public void shouldCorrectDeleteComment() {
        CommentDto commentSave = commentService.save(NOT_EXISTS_COMMENT);
        CommentDto commentBeforeDelete = commentService.getCommentById(commentSave.getId());
        commentService.delete(commentSave);

        assertThrows(NotFoundException.class, () -> commentService.getCommentById(commentSave.getId()));

        assertEqualsCommentDto(commentSave, commentBeforeDelete);
    }

    @DisplayName(" should throws NotFoundException if comment not exists")
    @Test
    public void shouldThrowsNotFoundExceptionIfCommentNotExists() {
        assertThrows(NotFoundException.class, () -> commentService.getCommentById(111L));
    }
}
