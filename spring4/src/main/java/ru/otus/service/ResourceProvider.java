package ru.otus.service;

import java.util.Locale;

public interface ResourceProvider {
    String getFileName();
    String getDelimiter();
    Locale getLocale();
}
