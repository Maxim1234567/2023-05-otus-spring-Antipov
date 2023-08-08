package ru.otus.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "books")
public class Book implements Cloneable {
    @Id
    private String id;

    private String name;

    private Integer yearIssue;

    private Integer numberPages;

    private List<Genre> genres;

    private List<Author> authors;

    private List<Comment> comments;
}