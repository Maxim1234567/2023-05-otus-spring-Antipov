package ru.otus.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode
public class BookDto {
    private Long id;
    private String name;
    private Integer yearIssue;
    private Integer numberPages;
    private List<GenreDto> genres;
    private List<AuthorDto> authors;
}
