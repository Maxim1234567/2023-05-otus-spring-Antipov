package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {
    private String id;

    private String series;

    private String number;

    private String issued;

    private String dateOfIssue;

    private String codeDivision;
}
