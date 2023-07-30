package ru.otus.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class CommentDto {
    private Long id;
    private String comments;
    private Long bookId;
}
