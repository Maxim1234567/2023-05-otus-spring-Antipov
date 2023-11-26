package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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