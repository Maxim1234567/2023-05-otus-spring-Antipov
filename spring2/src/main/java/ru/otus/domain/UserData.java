package ru.otus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserData {
    private final String firstName;
    private final String lastName;
}
