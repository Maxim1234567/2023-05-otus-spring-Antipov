package ru.otus.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
@EqualsAndHashCode
public class GenreDto {
    private Long id;
    private String genre;
}
