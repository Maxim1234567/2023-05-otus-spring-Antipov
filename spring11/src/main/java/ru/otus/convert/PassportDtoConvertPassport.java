package ru.otus.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.Passport;
import ru.otus.dto.PassportDto;

@Component
public class PassportDtoConvertPassport implements Converter<PassportDto, Passport> {
    @Override
    public Passport convert(PassportDto source) {
        return Passport.builder()
                .id(source.getId())
                .codeDivision(source.getCodeDivision())
                .dateOfIssue(source.getDateOfIssue())
                .issued(source.getIssued())
                .number(source.getNumber())
                .series(source.getSeries())
                .build();
    }
}
