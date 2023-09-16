package ru.otus.reactive;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.otus.convert.PassportConvertPassportDto;
import ru.otus.convert.PassportDtoConvertPassport;
import ru.otus.domain.Passport;
import ru.otus.dto.PassportDto;
import ru.otus.repository.PassportRepository;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class PassportRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(
            PassportRepository passportRepository,
            PassportDtoConvertPassport convertPassport,
            PassportConvertPassportDto convertPassportDto) {
        return route()
                .GET("/api/passport/{series}/{number}", accept(APPLICATION_JSON),
                        new PassportHandler(passportRepository,
                                convertPassport, convertPassportDto)::findBySeriesAndNumber)
                .GET("/api/passport/{id}", accept(APPLICATION_JSON),
                        new PassportHandler(passportRepository,
                                convertPassport, convertPassportDto)::findById)
                .GET("/api/passport", accept(APPLICATION_JSON),
                        new PassportHandler(passportRepository,
                                convertPassport, convertPassportDto)::list)
                .GET("/api/passport/{codeDivision}", accept(APPLICATION_JSON),
                        new PassportHandler(passportRepository,
                                convertPassport, convertPassportDto)::findByCodeDivision)
                .POST("/api/passport", accept(APPLICATION_JSON),
                        new PassportHandler(passportRepository,
                                convertPassport, convertPassportDto)::create)
                .PUT("/api/passport/{id}", accept(APPLICATION_JSON),
                        new PassportHandler(passportRepository,
                                convertPassport, convertPassportDto)::update)
                .build();
    }

    @RequiredArgsConstructor
    static class PassportHandler {

        private final PassportRepository passportRepository;

        private final PassportDtoConvertPassport convertPassport;

        private final PassportConvertPassportDto convertPassportDto;

        Mono<ServerResponse> findBySeriesAndNumber(ServerRequest serverRequest) {
            return ok().contentType(APPLICATION_JSON)
                    .body(passportRepository
                                    .findBySeriesAndNumber(serverRequest.pathVariable("series"),
                                            serverRequest.pathVariable("number"))
                                    .mapNotNull(convertPassportDto::convert)
                                    .switchIfEmpty(Mono.empty()),
                            PassportDto.class)
                    .switchIfEmpty(notFound().build());
        }

        Mono<ServerResponse> findByCodeDivision(ServerRequest serverRequest) {
            return ok().contentType(APPLICATION_JSON)
                    .body(passportRepository.findByCodeDivision(serverRequest.pathVariable("codeDivision"))
                            .mapNotNull(convertPassportDto::convert), PassportDto.class);
        }

        Mono<ServerResponse> findById(ServerRequest serverRequest) {
            return ok().contentType(APPLICATION_JSON)
                    .body(passportRepository.findById(serverRequest.pathVariable("id"))
                                    .mapNotNull(convertPassportDto::convert)
                                    .switchIfEmpty(Mono.empty()),
                            PassportDto.class)
                    .switchIfEmpty(notFound().build());
        }

        Mono<ServerResponse> list(ServerRequest serverRequest) {
            return ok().contentType(APPLICATION_JSON).body(passportRepository.findAll()
                    .mapNotNull(convertPassportDto::convert), PassportDto.class);
        }

        public Mono<ServerResponse> create(ServerRequest request) {
            Mono<Passport> passport = request.bodyToMono(PassportDto.class).mapNotNull(convertPassport::convert);

            return ServerResponse
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .body(fromPublisher(passport.flatMap(this::save).mapNotNull(convertPassportDto::convert),
                            PassportDto.class));
        }

        public Mono<ServerResponse> update(ServerRequest request) {
            String id = request.pathVariable("id");

            Mono<Passport> passport = request.bodyToMono(PassportDto.class).mapNotNull(convertPassport::convert);
            Mono<Passport> oldPassport = passportRepository.findById(id).subscribeOn(Schedulers.single());

            return oldPassport.flatMap(t -> ServerResponse
                            .ok()
                            .contentType(APPLICATION_JSON)
                            .body(fromPublisher(passport.flatMap(this::save).mapNotNull(convertPassportDto::convert),
                                    PassportDto.class)))
                    .switchIfEmpty(notFound().build());
        }

        private Mono<Passport> save(Passport passport) {
            return passportRepository
                    .save(passport)
                    .subscribeOn(Schedulers.single());
        }
    }
}
