package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.domain.Outgoing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private static final List<Outgoing> OUTGOINGS = List.of(
            Outgoing.builder()
                    .sender("GriffinPeter")
                    .recipients(
                            List.of(
                                    "GriffinLois", "GriffinBrain", "GriffinMeg", "GriffinChris", "GriffinStewie"
                            )
                    )
                    .message("I went to a drunken oyster")
                    .build(),
            Outgoing.builder()
                    .sender("GriffinChris")
                    .recipients(
                            List.of(
                                    "GriffinLois", "GriffinBrain", "GriffinMeg", "GriffinPeter", "GriffinStewie"
                            )
                    )
                    .message("There's an angry monkey in my closet")
                    .build(),
            Outgoing.builder()
                    .sender("GriffinPeter")
                    .recipients(
                            List.of(
                                    "GriffinLois", "GriffinBrain", "GriffinChris", "GriffinStewie"
                            )
                    )
                    .message("I farted in Meg's face")
                    .build(),
            Outgoing.builder()
                    .sender("GriffinLois")
                    .recipients(
                            List.of("GriffinLois")
                    )
                    .message("I went grocery shopping, look after the kids.")
                    .build(),
            Outgoing.builder()
                    .sender("GriffinStewie")
                    .recipients(
                            List.of(
                                    "GriffinBrain", "GriffinChris", "GriffinMeg"
                            )
                    )
                    .message("I want to kill Lois")
                    .build()
    );

    private final EmailGateway email;

    @Override
    public void startGenerateEmailLoop() {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        for (int i = 0; i < 10; i++) {
            int num = i + 1;

            pool.execute(() -> {
                Collection<Outgoing> items = generateOutgoings();

                log.info("{}, New emails: {}", num,
                        items.stream().map(Outgoing::toString).collect(Collectors.joining("\n")));

                email.process(items);
            });

            delay();
        }
    }

    private static Outgoing generateOutgoing() {
        return OUTGOINGS.get(RandomUtils.nextInt(0, OUTGOINGS.size()));
    }

    private static Collection<Outgoing> generateOutgoings() {
        List<Outgoing> items = new ArrayList<>();

        for (int i = 0; i < RandomUtils.nextInt(1, 5); i++) {
            items.add(generateOutgoing());
        }

        return items;
    }

    private void delay() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
