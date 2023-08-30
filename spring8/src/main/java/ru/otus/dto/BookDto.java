package ru.otus.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class BookDto {
    private String id;
    private String name;
    private Integer yearIssue;
    private Integer numberPages;
    private List<GenreDto> genres;
    private List<AuthorDto> authors;
    private List<CommentDto> comments;
}