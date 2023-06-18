package ru.otus.commandlinerunners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.otus.service.DemoInteraction;

@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@Component
@RequiredArgsConstructor
public class StartApplication implements CommandLineRunner {
    private final DemoInteraction interaction;

    @Override
    public void run(String... args) throws Exception {
        interaction.interaction();
    }
}
