package ru.otus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class BookDto {
    private Long id;

    @NotBlank(message = "{book-field-should-not-be-blank}")
    @Size(min = 3, max = 150, message = "{book-field-should-has-expected-size}")
    private String name;

    @NotNull(message = "{book-field-should-not-be-blank}")
    private Integer yearIssue;

    @NotNull(message = "{book-field-should-not-be-blank}")
    private Integer numberPages;

    private List<GenreDto> genres;

    private List<AuthorDto> authors;

    private List<CommentDto> comments;
}