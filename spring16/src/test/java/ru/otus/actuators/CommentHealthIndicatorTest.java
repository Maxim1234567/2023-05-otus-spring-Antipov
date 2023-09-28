package ru.otus.actuators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import ru.otus.property.CommentProperty;
import ru.otus.repository.CommentRepository;
import ru.otus.service.ResourceProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommentHealthIndicatorTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ResourceProvider resourceProvider;

    private CommentHealthIndicator commentHealthIndicator;

    @BeforeEach
    public void setUp() {
        commentHealthIndicator = new CommentHealthIndicator(
                commentRepository,
                resourceProvider
        );
    }

    @Test
    public void shouldCorrectReturnMessageIfCommentLessThenEnough() {
        CommentProperty comment = new CommentProperty(1000);

        given(commentRepository.count())
                .willReturn(100L);

        given(resourceProvider.getComment())
                .willReturn(comment);

        Health health = commentHealthIndicator.health();

        String status = health.getStatus().toString();
        String message = health.getDetails().get("message").toString();

        assertEquals(status, "UP");
        assertEquals(message, "Enough comments");
    }

    @Test
    public void shouldCorrectReturnMessageIfCommentMoreThenEnough() {
        CommentProperty comment = new CommentProperty(1000);

        given(commentRepository.count())
                .willReturn(1001L);

        given(resourceProvider.getComment())
                .willReturn(comment);

        Health health = commentHealthIndicator.health();

        String status = health.getStatus().toString();
        String message = health.getDetails().get("message").toString();

        assertEquals(status, "DOWN");
        assertEquals(message, "Too many comments");
    }
}
