package ru.otus.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Document(collection = "genres")
public class Genre {
    @Id
    private String id;

    private String genre;
}