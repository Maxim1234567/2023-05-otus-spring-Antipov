package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PassportControllerTest {

    @Autowired
    private WebTestClient webClient;

    @DisplayName("should correctly return view list-passport")
    @Test
    public void shouldReturnViewListPassport() throws Exception {
        webClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(
                        body -> body.getResponseBody().equals("/")
                );
    }

    @DisplayName("should correctly return view update-passport")
    @Test
    public void shouldReturnViewUpdatePassport() throws Exception {
        webClient.get().uri(uriBuilder ->
                uriBuilder.path("/passport/update")
                        .queryParam("id", "100")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(
                        body -> body.getResponseBody().equals("update-passport")
                );
    }

    @DisplayName("should correctly return view create-passport")
    @Test
    public void shouldReturnViewCreatePassport() throws Exception {
        webClient.get().uri("/passport/create")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(
                        body -> body.getResponseBody().equals("create-passport")
                );
    }

    @DisplayName("should correctly return view info-passport")
    @Test
    public void shouldReturnViewInfoPassport() throws Exception {
        webClient.get().uri(uriBuilder ->
                uriBuilder.path("/passport/info")
                        .queryParam("series", "1")
                        .queryParam("number", "2")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(
                        body -> body.getResponseBody().equals("info-passport")
                );
    }
}
