package ru.otus.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.nonrelation.domain.CharacterNonRelation;
import ru.otus.nonrelation.domain.EpisodeNonRelation;
import ru.otus.relation.domain.CharacterRelation;
import ru.otus.relation.domain.EpisodeRelation;
import ru.otus.service.TransformService;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class JobConfig {

    public static final String IMPORT_JOB_NAME = "importNameJob";

    private static final int CHUNK_SIZE = 5;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @StepScope
    @Bean
    public MongoItemReader<CharacterNonRelation> readerCharacter(MongoTemplate template) {
        return new MongoItemReaderBuilder<CharacterNonRelation>()
                .name("characterItemReader")
                .targetType(CharacterNonRelation.class)
                .jsonQuery("[]")
                .template(template)
                .sorts(Map.of())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<EpisodeNonRelation> readerEpisode(MongoTemplate template) {
        return new MongoItemReaderBuilder<EpisodeNonRelation>()
                .name("episodeItemReader")
                .targetType(EpisodeNonRelation.class)
                .jsonQuery("[]")
                .template(template)
                .sorts(Map.of())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<CharacterNonRelation, CharacterRelation> processorCharacter(
            TransformService transformService
    ) {
        return transformService::character;
    }

    @StepScope
    @Bean
    public ItemProcessor<EpisodeNonRelation, EpisodeRelation> processorEpisode(TransformService transformService) {
        return transformService::episode;
    }

    @StepScope
    @Bean
    public JpaItemWriter<CharacterRelation> writerCharacter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<CharacterRelation>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @StepScope
    @Bean
    public JpaItemWriter<EpisodeRelation> writerEpisode(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<EpisodeRelation>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Job importCharacterJob(Step transformCharacterStep, Step transformEpisodeStep) {
        return new JobBuilder(IMPORT_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(transformCharacterStep)
                .next(transformEpisodeStep)
                .build();
    }

    @Bean
    public Step transformCharacterStep(
            MongoItemReader<CharacterNonRelation> readerCharacter,
            JpaItemWriter<CharacterRelation> writerCharacter,
            ItemProcessor<CharacterNonRelation, CharacterRelation> processorCharacter
    ) {
        return new StepBuilder("transformCharacterStep", jobRepository)
                .<CharacterNonRelation, CharacterRelation>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(readerCharacter)
                .processor(processorCharacter)
                .writer(writerCharacter)
                .build();
    }

    @Bean
    public Step transformEpisodeStep(
            MongoItemReader<EpisodeNonRelation> readerEpisode,
            JpaItemWriter<EpisodeRelation> writerEpisode,
            ItemProcessor<EpisodeNonRelation, EpisodeRelation> processorEpisode
    ) {
        return new StepBuilder("transformEpisodeStep", jobRepository)
                .<EpisodeNonRelation, EpisodeRelation>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(readerEpisode)
                .processor(processorEpisode)
                .writer(writerEpisode)
                .build();
    }
}
