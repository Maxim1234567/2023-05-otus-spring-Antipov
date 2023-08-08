package ru.otus.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    private String comments;

    private String bookId;
}