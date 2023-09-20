package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Properties;

import static ru.otus.config.JobConfig.IMPORT_JOB_NAME;

@ShellComponent
@RequiredArgsConstructor
public class BatchCommands {

    private final JobOperator jobOperator;

    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws Exception {
        jobOperator.start(IMPORT_JOB_NAME, new Properties());
    }
}
