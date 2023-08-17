package ru.otus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @Size(min = 1, message = "{book-list-field-should-has-one-element}")
    private List<GenreDto> genres;

    @Size(min = 1, message = "{book-list-field-should-has-one-element}")
    private List<AuthorDto> authors;

    @Size(min = 1, message = "{book-list-field-should-has-one-element}")
    private List<CommentDto> comments;
}