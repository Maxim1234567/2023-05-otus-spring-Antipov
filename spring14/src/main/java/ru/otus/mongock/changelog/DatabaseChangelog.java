package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.nonrelation.domain.CharacterNonRelation;
import ru.otus.nonrelation.domain.EpisodeNonRelation;
import ru.otus.nonrelation.repository.CharacterNonRelationRepository;
import ru.otus.nonrelation.repository.EpisodeNonRelationRepository;

import java.util.List;
import java.util.UUID;

@ChangeLog
public class DatabaseChangelog {
    private static final String CHARACTER_1 = randomUuid();

    private static final String CHARACTER_2 = randomUuid();

    private static final String CHARACTER_3 = randomUuid();

    private static final String CHARACTER_4 = randomUuid();

    private static final String CHARACTER_5 = randomUuid();

    private static final String CHARACTER_6 = randomUuid();

    private static final String CHARACTER_7 = randomUuid();

    private static final String CHARACTER_8 = randomUuid();

    private static final String CHARACTER_9 = randomUuid();

    private static final String EPISODE_1 = randomUuid();

    private static final String EPISODE_2 = randomUuid();

    private static final String EPISODE_3 = randomUuid();

    private static final String EPISODE_4 = randomUuid();

    private static final String EPISODE_5 = randomUuid();

    private static final String EPISODE_6 = randomUuid();

    private final List<CharacterNonRelation> characterNonRelations = List.of(
            CharacterNonRelation.builder()
                    .id(CHARACTER_1)
                    .firstName("Peter")
                    .lastName("Griffin")
                    .colorSkin("white")
                    .isDisabled(false)
                    .build(),
            CharacterNonRelation.builder()
                    .id(CHARACTER_2)
                    .firstName("Lois")
                    .lastName("Griffin")
                    .colorSkin("white")
                    .isDisabled(false)
                    .build(),
            CharacterNonRelation.builder()
                    .id(CHARACTER_3)
                    .firstName("Brain")
                    .lastName("Griffin")
                    .colorSkin("white")
                    .isDisabled(false)
                    .build(),
            CharacterNonRelation.builder()
                    .id(CHARACTER_4)
                    .firstName("Meg")
                    .lastName("Griffin")
                    .colorSkin("white")
                    .isDisabled(false)
                    .build(),
            CharacterNonRelation.builder()
                    .id(CHARACTER_5)
                    .firstName("Chris")
                    .lastName("Griffin")
                    .colorSkin("white")
                    .isDisabled(false)
                    .build(),
            CharacterNonRelation.builder()
                    .id(CHARACTER_6)
                    .firstName("Stewie")
                    .lastName("Griffin")
                    .colorSkin("white")
                    .isDisabled(false)
                    .build(),
            CharacterNonRelation.builder()
                    .id(CHARACTER_7)
                    .firstName("Joe")
                    .lastName("Swanson")
                    .colorSkin("white")
                    .isDisabled(true)
                    .build(),
            CharacterNonRelation.builder()
                    .id(CHARACTER_8)
                    .firstName("Cleveland")
                    .lastName("Brow")
                    .colorSkin("black")
                    .isDisabled(false)
                    .build(),
            CharacterNonRelation.builder()
                    .id(CHARACTER_9)
                    .firstName("Glen")
                    .lastName("Quagmire")
                    .colorSkin("black")
                    .isDisabled(false)
                    .build()
    );

    private final List<EpisodeNonRelation> episodeNonRelations = List.of(
            EpisodeNonRelation.builder()
                    .id(EPISODE_1)
                    .name("And Death has a shadow")
                    .season(1)
                    .episode(1)
                    .characterNonRelations(
                            List.of(
                                getCharacter("Peter"),
                                getCharacter("Lois"),
                                getCharacter("Chris"),
                                getCharacter("Joe")
                            )
                    )
                    .build(),
            EpisodeNonRelation.builder()
                    .id(EPISODE_2)
                    .name("Peter, Peter - lover of black caviar")
                    .season(2)
                    .episode(1)
                    .characterNonRelations(
                            List.of(
                                    getCharacter("Brain"),
                                    getCharacter("Stewie"),
                                    getCharacter("Glen")
                            )
                    )
                    .build(),
            EpisodeNonRelation.builder()
                    .id(EPISODE_3)
                    .name("Thin white line")
                    .season(3)
                    .episode(1)
                    .characterNonRelations(
                            List.of(
                                    getCharacter("Meg"),
                                    getCharacter("Cleveland"),
                                    getCharacter("Peter"),
                                    getCharacter("Glen"),
                                    getCharacter("Chris")
                            )
                    )
                    .build(),
            EpisodeNonRelation.builder()
                    .id(EPISODE_4)
                    .name("North through North Quahog")
                    .season(4)
                    .episode(1)
                    .characterNonRelations(
                            List.of(
                                    getCharacter("Peter"),
                                    getCharacter("Lois")
                            )
                    )
                    .build(),
            EpisodeNonRelation.builder()
                    .id(EPISODE_5)
                    .name("Stewie loves Lois")
                    .season(5)
                    .episode(1)
                    .characterNonRelations(
                            List.of(
                                    getCharacter("Glen"),
                                    getCharacter("Cleveland"),
                                    getCharacter("Joe")
                            )
                    )
                    .build(),
            EpisodeNonRelation.builder()
                    .id(EPISODE_6)
                    .name("Blue Harvest")
                    .season(6)
                    .episode(1)
                    .characterNonRelations(
                            List.of(
                                    getCharacter("Stewie")
                            )
                    )
                    .build()
    );

    @ChangeSet(order = "001", id = "dropDB", author = "antipov", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "initCharacters", author = "antipov", runAlways = true)
    public void initCharacters(CharacterNonRelationRepository characterNonRelationRepository) {
        characterNonRelationRepository.save(characterNonRelations.get(0));
        characterNonRelationRepository.save(characterNonRelations.get(1));
        characterNonRelationRepository.save(characterNonRelations.get(2));
        characterNonRelationRepository.save(characterNonRelations.get(3));
        characterNonRelationRepository.save(characterNonRelations.get(4));
        characterNonRelationRepository.save(characterNonRelations.get(5));
        characterNonRelationRepository.save(characterNonRelations.get(6));
        characterNonRelationRepository.save(characterNonRelations.get(7));
        characterNonRelationRepository.save(characterNonRelations.get(8));
    }

    @ChangeSet(order = "003", id = "initEpisodes", author = "antipov", runAlways = true)
    public void initEpisodes(EpisodeNonRelationRepository episodeNonRelationRepository) {
        episodeNonRelationRepository.save(episodeNonRelations.get(0));
        episodeNonRelationRepository.save(episodeNonRelations.get(1));
        episodeNonRelationRepository.save(episodeNonRelations.get(2));
        episodeNonRelationRepository.save(episodeNonRelations.get(3));
        episodeNonRelationRepository.save(episodeNonRelations.get(4));
        episodeNonRelationRepository.save(episodeNonRelations.get(5));
    }

    private CharacterNonRelation getCharacter(String firstName) {
        return characterNonRelations.stream()
                .filter(c -> c.getFirstName().equals(firstName))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private static String randomUuid() {
        return String.valueOf(UUID.randomUUID());
    }
}
