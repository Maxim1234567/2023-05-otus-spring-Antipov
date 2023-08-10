package ru.otus.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookDto {
    private Long id;

    private String name;

    private Integer yearIssue;

    private Integer numberPages;

    private List<GenreDto> genres;

    private List<AuthorDto> authors;

    private List<CommentDto> comments;
}
