package ru.otus.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.domain.Outgoing;

import java.util.Collection;

@MessagingGateway
public interface EmailGateway {
    @Gateway(requestChannel = "messageChannel")
    void process(Collection<Outgoing> outgoings);
}
