package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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