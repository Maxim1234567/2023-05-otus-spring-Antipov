package ru.otus.reactive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.dto.PassportDto;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Route to work with passport should")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PassportRouterTest {
    private final List<PassportDto> passports = List.of(
            PassportDto.builder().id("1").series("0000").number("111111").issued("there").dateOfIssue("00.00.0000").codeDivision("000-000").build(),
            PassportDto.builder().id("2").series("0001").number("111110").issued("there").dateOfIssue("00.00.1000").codeDivision("000-000").build(),
            PassportDto.builder().id("3").series("0010").number("111100").issued("where").dateOfIssue("00.00.2000").codeDivision("000-000").build(),
            PassportDto.builder().id("4").series("0011").number("111000").issued("here").dateOfIssue("00.00.3000").codeDivision("001-000").build(),
            PassportDto.builder().id("5").series("0100").number("110000").issued("here").dateOfIssue("00.00.4000").codeDivision("001-000").build()
    );

    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("correctly return all passports")
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

        assertEqualsListPassport(
                result.stream().filter(p -> !p.getId().equals("1")).toList(),
                passports.stream().filter(p -> !p.getId().equals("1")).toList());
    }

    @DisplayName("correctly return all passports by code division")
    @Test
    void shouldCorrectReturnAllPassportsByCodeDivision() {
        String codeDivision = "001-000";

        var client = WebClient.create(String.format("http://localhost:%d", port));
        var expectedSize = 2;

        List<PassportDto> result = client
                .get().uri(uriBuilder -> uriBuilder.path("/api/passport/{codeDivision}").build(codeDivision))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(PassportDto.class)
                .take(expectedSize)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();

        assertEqualsListPassport(result, passports
                .stream()
                .filter(p -> p.getCodeDivision().equals(codeDivision))
                .toList());
    }

    @Test
    @DisplayName("correctly return passport by series and number")
    void shouldCorrectReturnPassportBySeriesAndNumber() {
        String series = "0000";
        String number = "111111";

        var client = WebClient.create(String.format("http://localhost:%d", port));

        PassportDto result = client
                .get().uri(uriBuilder -> uriBuilder.path("/api/passport/{series}/{number}").build(series, number))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PassportDto.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertEqualsPassport(result, passports
                .stream()
                .filter(p -> p.getSeries().equals(series) && p.getNumber().equals(number))
                .findFirst()
                .orElse(PassportDto.builder().build()));
    }

    @Test
    @DisplayName("correctly return passport by id")
    void shouldCorrectReturnPassportById() {
        String id = "1";

        var client = WebClient.create(String.format("http://localhost:%d", port));

        PassportDto result = client
                .get().uri(uriBuilder -> uriBuilder.path("/api/passport/{id}").build(id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PassportDto.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertEqualsPassport(result, passports
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(PassportDto.builder().build()));
    }

    @Test
    @DisplayName("correctly save passport")
    void shouldCorrectCreatePassport() {
        var client = WebClient.create(String.format("http://localhost:%d", port));

        PassportDto passport = PassportDto.builder()
                .series("1000")
                .number("110001")
                .dateOfIssue("00.00.5000")
                .codeDivision("001-001")
                .issued("where")
                .build();

        PassportDto result = client.post()
                .uri("/api/passport")
                .bodyValue(passport)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PassportDto.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result).isNotNull()
                .matches(p -> Objects.nonNull(p.getId()))
                .matches(p -> p.getSeries().equals(passport.getSeries()))
                .matches(p -> p.getNumber().equals(passport.getNumber()))
                .matches(p -> p.getIssued().equals(passport.getIssued()))
                .matches(p -> p.getDateOfIssue().equals(passport.getDateOfIssue()))
                .matches(p -> p.getCodeDivision().equals(passport.getCodeDivision()));
    }

    @Test
    @DisplayName("correctly update passport")
    void shouldCorrectUpdatePassport() {
        var client = WebClient.create(String.format("http://localhost:%d", port));

        PassportDto passport = PassportDto.builder()
                .id("1")
                .series("1000")
                .number("110001")
                .dateOfIssue("00.00.5000")
                .codeDivision("001-001")
                .issued("where")
                .build();

        PassportDto result = client.put()
                .uri(uriBuilder -> uriBuilder.path("/api/passport/{id}").build("1"))
                .bodyValue(passport)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PassportDto.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertEqualsPassport(passport, result);
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
