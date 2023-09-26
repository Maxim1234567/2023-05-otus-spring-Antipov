package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.relation.repository.CharacterRelationalRepository;
import ru.otus.relation.repository.EpisodeRelationalRepository;

import java.util.Properties;

import static ru.otus.config.JobConfig.IMPORT_JOB_NAME;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class BatchCommands {

    private final JobOperator jobOperator;

    private final CharacterRelationalRepository characterRepository;

    private final EpisodeRelationalRepository episodeRepository;

    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws Exception {
        log.info("Before Job Migration");
        characterRepository.findAll().forEach(c -> log.info("{}", c));
        episodeRepository.findAll().forEach(e -> log.info("{}", e));

        jobOperator.start(IMPORT_JOB_NAME, new Properties());

        log.info("After Job Migration");
        characterRepository.findAll().forEach(c -> log.info("{}", c));
        episodeRepository.findAll().forEach(e -> log.info("{}", e));
    }
}
