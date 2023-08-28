package ru.otus.reactive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.test.StepVerifier;
import ru.otus.dto.PassportDto;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Route to work with passport should")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PassportRouterTest {
    private final List<PassportDto> passports = List.of(
            PassportDto.builder().id("1").series("0000").number("111111").issued("there").dateOfIssue("00.00.0000").codeDivision("000-001").build(),
            PassportDto.builder().id("2").series("0001").number("111110").issued("there").dateOfIssue("00.00.1000").codeDivision("000-010").build(),
            PassportDto.builder().id("3").series("0010").number("111100").issued("where").dateOfIssue("00.00.2000").codeDivision("000-100").build(),
            PassportDto.builder().id("4").series("0011").number("111000").issued("here").dateOfIssue("00.00.3000").codeDivision("001-000").build(),
            PassportDto.builder().id("5").series("0100").number("110000").issued("here").dateOfIssue("00.00.4000").codeDivision("010-000").build()
    );

    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("correctly return all passport")
    void shouldCorrectReturnAllPassports() {
        var client = WebClient.create(String.format("http://localhost:%d", port));
        var expectedSize = 5;

        List<PassportDto> result = client
                .get().uri("/api/passport")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(PassportDto.class)
                .take(expectedSize)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();

        assertEqualsListPassport(result, passports);
    }

    private void assertEqualsPassport(PassportDto excepted, PassportDto result) {
        assertThat(result).isNotNull()
                .matches(r -> r.getId().equals(excepted.getId()))
                .matches(r -> r.getSeries().equals(excepted.getSeries()))
                .matches(r -> r.getNumber().equals(excepted.getNumber()))
                .matches(r -> r.getCodeDivision().equals(excepted.getCodeDivision()))
                .matches(r -> r.getIssued().equals(excepted.getIssued()))
                .matches(r -> r.getDateOfIssue().equals(excepted.getDateOfIssue()));
    }

    private void assertEqualsListPassport(List<PassportDto> excepted, List<PassportDto> result) {
        List<PassportDto> list1 = excepted.stream().sorted(Comparator.comparing(PassportDto::getId)).toList();
        List<PassportDto> list2 = result.stream().sorted(Comparator.comparing(PassportDto::getId)).toList();

        for(int i = 0; i < list1.size(); i++) {
            assertEqualsPassport(list1.get(i), list2.get(i));
        }
    }
}
