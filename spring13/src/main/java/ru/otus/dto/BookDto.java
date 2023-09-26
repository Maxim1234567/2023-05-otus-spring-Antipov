package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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