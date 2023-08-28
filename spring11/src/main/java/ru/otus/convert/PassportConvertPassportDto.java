package ru.otus.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Passport;
import ru.otus.dto.PassportDto;

@Component
public class PassportConvertPassportDto implements Converter<Passport, PassportDto> {
    @Override
    public PassportDto convert(Passport source) {
        return PassportDto.builder()
                .id(source.getId())
                .dateOfIssue(source.getDateOfIssue())
                .codeDivision(source.getCodeDivision())
                .issued(source.getIssued())
                .number(source.getNumber())
                .series(source.getSeries())
                .build();
    }
}
