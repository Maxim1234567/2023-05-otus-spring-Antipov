package ru.otus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class AuthorDto {
    private Long id;

    @NotBlank(message = "{author-field-should-not-be-blank}")
    @Size(min = 3, max = 150, message = "{author-field-should-has-expected-size}")
    private String firstName;

    @NotBlank(message = "{author-field-should-not-be-blank}")
    @Size(min = 3, max = 150, message = "{author-field-should-has-expected-size}")
    private String lastName;

    @NotNull(message = "{author-field-should-not-be-blank}")
    @Range(min = 18, max = 99, message = "{author-age-field-should-has-expected-size}")
    private int age;

    @NotNull(message = "{author-field-should-not-be-blank}")
    private int yearBirthdate;

    public String getName() {
        return firstName + " " + lastName;
    }
}