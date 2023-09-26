package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.nonrelation.domain.CharacterNonRelation;
import ru.otus.nonrelation.domain.EpisodeNonRelation;
import ru.otus.relation.domain.CharacterRelation;
import ru.otus.relation.domain.EpisodeRelation;
import ru.otus.relation.repository.CharacterRelationalRepository;
import ru.otus.relation.repository.EpisodeRelationalRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransformService {

    private final CharacterRelationalRepository characterRepository;

    private final EpisodeRelationalRepository episodeRepository;

    private final Map<String, Long> characterIds = new HashMap<>();

    private final Map<String, Long> episodeIds = new HashMap<>();

    public CharacterRelation character(CharacterNonRelation characterNonRelation) {
        putCharacterId(characterNonRelation.getId());

        return CharacterRelation.builder()
                .id(getCharacterId(characterNonRelation.getId()))
                .firstName(characterNonRelation.getFirstName())
                .lastName(characterNonRelation.getLastName())
                .colorSkin(characterNonRelation.getColorSkin())
                .isDisabled(characterNonRelation.isDisabled())
                .build();
    }

    public EpisodeRelation episode(EpisodeNonRelation episodeNonRelation) {
        putEpisodeId(episodeNonRelation.getId());

        List<CharacterRelation> characters = episodeNonRelation.getCharacterNonRelations().stream()
                .map(this::character)
                .toList();

        return EpisodeRelation.builder()
                .id(getEpisodeId(episodeNonRelation.getId()))
                .name(episodeNonRelation.getName())
                .season(episodeNonRelation.getSeason())
                .episode(episodeNonRelation.getEpisode())
                .characterRelations(characters)
                .build();
    }

    private void putCharacterId(String uuid) {
        if (characterIds.get(uuid) == null) {
            characterIds.put(uuid, characterRepository.getIdNextVal());
        }
    }

    private Long getCharacterId(String uuid) {
        return characterIds.get(uuid);
    }

    private void putEpisodeId(String uuid) {
        if (episodeIds.get(uuid) == null) {
            episodeIds.put(uuid, episodeRepository.getIdNextVal());
        }
    }

    private Long getEpisodeId(String uuid) {
        return episodeIds.get(uuid);
    }
}