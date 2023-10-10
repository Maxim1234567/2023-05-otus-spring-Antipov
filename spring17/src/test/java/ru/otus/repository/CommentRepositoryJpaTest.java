package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.Utils.assertEqualsComment;
import static ru.otus.Utils.assertEqualsCommentList;

@DisplayName("Dao to work with comment should")
@DataJpaTest
public class CommentRepositoryJpaTest {
    private static final Comment EXISTING_COMMENT = new Comment(
            100L, "Good Book!",
            Book.builder()
                    .id(100L)
                    .name("Java. Complete guide")
                    .yearIssue(2022)
                    .numberPages(1344)
                    .authors(Collections.emptyList())
                    .genres(Collections.emptyList())
                    .comments(Collections.emptyList())
                    .build()
    );

    private static final List<Comment> EXPECTED_COMMENTS_BY_BOOK_ID = List.of(
            new Comment(100L, "Good Book!",
                    Book.builder()
                            .id(100L)
                            .name("Java. Complete guide")
                            .yearIssue(2022)
                            .numberPages(1344)
                            .authors(Collections.emptyList())
                            .genres(Collections.emptyList())
                            .comments(Collections.emptyList())
                            .build()),
            new Comment(200L, "Very Interesting!",
                    Book.builder()
                            .id(100L)
                            .name("Java. Complete guide")
                            .yearIssue(2022)
                            .numberPages(1344)
                            .authors(Collections.emptyList())
                            .genres(Collections.emptyList())
                            .comments(Collections.emptyList())
                            .build()),
            new Comment(300L, "I cried when I read it",
                    Book.builder()
                            .id(100L)
                            .name("Java. Complete guide")
                            .yearIssue(2022)
                            .numberPages(1344)
                            .authors(Collections.emptyList())
                            .genres(Collections.emptyList())
                            .comments(Collections.emptyList())
                            .build())
    );

    private static final Comment NOT_EXISTS_COMMENT = new Comment(
            null, "Read the book many times",
            Book.builder()
                    .id(200L)
                    .name("Starships. Andromeda's nebula")
                    .yearIssue(1987)
                    .numberPages(400)
                    .authors(Collections.emptyList())
                    .genres(Collections.emptyList())
                    .comments(Collections.emptyList())
                    .build()
    );

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName(" correctly save comment")
    @Test
    public void shouldCorrectSaveComment() {
        Comment comment = commentRepository.save(NOT_EXISTS_COMMENT);
        Optional<Comment> result = commentRepository.findById(comment.getId());

        assertTrue(result.isPresent());
        assertEqualsComment(comment, result.get());
    }

    @DisplayName(" correctly update comment")
    @Test
    public void shouldCorrectUpdateComment() {
        Comment comment = commentRepository.findById(EXISTING_COMMENT.getId()).get();
        comment.setComments("NEW COMMENT");
        commentRepository.save(comment);
        Comment updateComment = commentRepository.findById(EXISTING_COMMENT.getId()).get();

        assertEqualsComment(comment, updateComment);
    }

    @DisplayName(" correctly delete comment")
    @Test
    public void shouldCorrectDeleteComment() {
        Optional<Comment> comment = commentRepository.findById(EXISTING_COMMENT.getId());
        commentRepository.deleteById(EXISTING_COMMENT.getId());
        Optional<Comment> deleteComment = commentRepository.findById(EXISTING_COMMENT.getId());

        assertEqualsComment(comment.get(), EXISTING_COMMENT);
        assertTrue(deleteComment.isEmpty());
    }

    @DisplayName(" correctly return comment by id")
    @Test
    public void shouldCorrectFindCommentById() {
        Optional<Comment> comment = commentRepository.findById(EXISTING_COMMENT.getId());

        assertTrue(comment.isPresent());
        assertEqualsComment(comment.get(), EXISTING_COMMENT);
    }

    @DisplayName(" correctly return all comments by book id")
    @Test
    public void shouldCorrectFindAllCommentsByBookId() {
        List<Comment> comments = commentRepository.findAllByBookId(100L);
        assertEqualsCommentList(EXPECTED_COMMENTS_BY_BOOK_ID, comments);
    }

    @DisplayName(" correctly return comments by ids")
    @Test
    public void shouldCorrectReturnCommentsByIds() {
        List<Long> commentIds = EXPECTED_COMMENTS_BY_BOOK_ID
                .stream()
                .map(Comment::getId)
                .toList();

        List<Comment> comments = commentRepository
                .findByIds(commentIds);

        assertEqualsCommentList(EXPECTED_COMMENTS_BY_BOOK_ID, comments);
    }
}