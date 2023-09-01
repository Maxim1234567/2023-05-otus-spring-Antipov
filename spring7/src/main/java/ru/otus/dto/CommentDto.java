package ru.otus.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {
    private Long id;
    private String comments;
    private BookDto book;
}
