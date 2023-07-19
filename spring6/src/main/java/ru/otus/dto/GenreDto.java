package ru.otus.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode
public class GenreDto {
    private Long id;
    private String genre;
}
