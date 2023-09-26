package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.domain.Incoming;
import ru.otus.domain.Outgoing;

import java.util.List;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Override
    public List<Incoming> sending(Outgoing outgoing) {
        log.info("Outgoing {}", outgoing);

        List<Incoming> incomings = outgoing.getRecipients().stream()
                        .map(recipient -> Incoming.builder()
                                .recipient(recipient)
                                .sender(outgoing.getSender())
                                .message(outgoing.getMessage())
                                .build())
                                .toList();

        incomings.forEach(
                incoming -> log.info("Incoming {}", incoming)
        );

        delay();

        return incomings;
    }

    private static void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
