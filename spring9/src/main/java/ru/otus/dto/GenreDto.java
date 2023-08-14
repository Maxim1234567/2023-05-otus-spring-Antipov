package ru.otus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class GenreDto {
    private Long id;

    @NotBlank(message = "{genre-field-should-not-be-blank}")
    @Size(min = 3, max = 50, message = "{genre-field-should-has-expected-size}")
    private String genre;
}