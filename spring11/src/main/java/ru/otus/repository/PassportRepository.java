package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Passport;

public interface PassportRepository extends ReactiveMongoRepository<Passport, String> {
    Mono<Passport> findBySeriesAndNumber(String series, String number);
    Flux<Passport> findByCodeDivision(String codeDivision);
}
