package ru.otus.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.repository.CommentRepository;

@Component
@RequiredArgsConstructor
public class CommentHealthIndicator implements HealthIndicator {

    private final CommentRepository commentRepository;

    @Override
    public Health health() {
        boolean isManyComments = commentRepository.count() >= 1000;

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
