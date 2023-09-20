package ru.otus.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.Message;
import ru.otus.domain.Incoming;
import ru.otus.service.EmailService;

@Configuration
@Slf4j
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> messageChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public DirectChannelSpec peterGriffinChannel() {
        DirectChannelSpec directChannelSpec = MessageChannels.direct("GriffinPeter");
        directChannelSpec.getObject().subscribe(this::processingIncomingMessage);
        return directChannelSpec;
    }

    @Bean
    public DirectChannelSpec loisGriffinChannel() {
        DirectChannelSpec directChannelSpec = MessageChannels.direct("GriffinLois");
        directChannelSpec.getObject().subscribe(this::processingIncomingMessage);
        return directChannelSpec;
    }

    @Bean
    public DirectChannelSpec brainGriffinChannel() {
        DirectChannelSpec directChannelSpec = MessageChannels.direct("GriffinBrain");
        directChannelSpec.getObject().subscribe(this::processingIncomingMessage);
        return directChannelSpec;
    }

    @Bean
    public DirectChannelSpec megGriffinChannel() {
        DirectChannelSpec directChannelSpec = MessageChannels.direct("GriffinMeg");
        directChannelSpec.getObject().subscribe(this::processingIncomingMessage);
        return directChannelSpec;
    }

    @Bean
    public DirectChannelSpec chrisGriffinChannel() {
        DirectChannelSpec directChannelSpec = MessageChannels.direct("GriffinChris");
        directChannelSpec.getObject().subscribe(this::processingIncomingMessage);
        return directChannelSpec;
    }

    @Bean
    public DirectChannelSpec stewieGriffinChannel() {
        DirectChannelSpec directChannelSpec = MessageChannels.direct("GriffinStewie");
        directChannelSpec.getObject().subscribe(this::processingIncomingMessage);
        return directChannelSpec;
    }

    @Bean
    public IntegrationFlow emailFlow(EmailService emailService) {
        return IntegrationFlow.from(messageChannel())
                .split()
                .handle(emailService, "sending")
                .split()
                .<Incoming, String>route(
                        Incoming::getRecipient,
                        mapping ->
                            mapping
                                .subFlowMapping("GriffinPeter", msg -> msg.channel(peterGriffinChannel()))
                                .subFlowMapping("GriffinLois", msg -> msg.channel(loisGriffinChannel()))
                                .subFlowMapping("GriffinBrain", msg -> msg.channel(brainGriffinChannel()))
                                .subFlowMapping("GriffinMeg", msg -> msg.channel(megGriffinChannel()))
                                .subFlowMapping("GriffinChris", msg -> msg.channel(chrisGriffinChannel()))
                                .subFlowMapping("GriffinStewie", msg -> msg.channel(stewieGriffinChannel()))
                ).get();
    }

    private void processingIncomingMessage(Message<?> msg) {
        Incoming payload = (Incoming) msg.getPayload();

        log.info("Incoming message from {} to {}: {}",
                payload.getSender(), payload.getRecipient(), payload.getMessage());
    }
}
