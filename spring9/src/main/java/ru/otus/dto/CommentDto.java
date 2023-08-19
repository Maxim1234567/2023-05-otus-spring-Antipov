package ru.otus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {
    private Long id;

    @NotBlank(message = "{comment-field-should-not-be-blank}")
    @Size(min = 3, max = 150, message = "{comment-field-should-has-expected-size}")
    private String comments;

    private BookDto book;
}