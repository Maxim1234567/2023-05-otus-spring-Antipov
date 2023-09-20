package ru.otus.service;

import ru.otus.domain.Incoming;
import ru.otus.domain.Outgoing;

import java.util.List;

public interface EmailService {
    List<Incoming> sending(Outgoing outgoing);
}
