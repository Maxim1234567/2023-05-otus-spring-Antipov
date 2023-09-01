package ru.otus.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {
    private String id;
    private String comments;
    private String bookId;
}