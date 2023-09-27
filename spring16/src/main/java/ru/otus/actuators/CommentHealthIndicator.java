package ru.otus.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.repository.CommentRepository;
import ru.otus.service.ResourceProvider;

@Component
@RequiredArgsConstructor
public class CommentHealthIndicator implements HealthIndicator {

    private final CommentRepository commentRepository;

    private final ResourceProvider resourceProvider;

    @Override
    public Health health() {
        boolean isManyComments = commentRepository.count() >= resourceProvider.getComment().getEnough();

        if(isManyComments) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Too many comments")
                    .build();
        } else {
            return Health.up().withDetail("message", "Enough comments").build();
        }
    }
}
