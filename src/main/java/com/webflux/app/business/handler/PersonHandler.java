package com.webflux.app.business.handler;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.exception.BadRequestException;
import com.webflux.app.business.exception.ValidationException;
import com.webflux.app.business.repository.PersonRepository;
import com.webflux.app.business.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static reactor.core.publisher.Mono.fromCallable;

@Component
public class PersonHandler {

  private final PersonService personService;
  private final PersonRepository personRepository;

  public PersonHandler(final PersonService personService, final PersonRepository personRepository) {
    this.personService = personService;
    this.personRepository = personRepository;
  }

  public Mono<ServerResponse> create(final ServerRequest serverRequest) {
    return serverRequest.bodyToMono(Person.class)
            .flatMap(personService::save)
            .flatMap(personCreate -> ok().body(fromObject(personCreate)))
            .switchIfEmpty(Mono.defer(() -> notFound().build()))
            .onErrorMap(ValidationException.class, BadRequestException::new);
  }

  public Mono<ServerResponse> delete(final ServerRequest serverRequest) {
    final Long id = Long.valueOf(serverRequest.pathVariable("id"));
    return Mono.just(serverRequest.queryParams())
            .flatMap(deletePerson -> personService.delete(id))
            .flatMap(personUpdate -> ok().body(fromObject(personUpdate)))
            .onErrorMap(ValidationException.class, BadRequestException::new);
  }

  public Mono<ServerResponse> findAll() {
    return fromCallable(personRepository::findAll)
            .publishOn(Schedulers.elastic())
            .flatMap(person -> ok().body(fromObject(person)))
            .onErrorMap(ValidationException.class, BadRequestException::new);
  }

  public Mono<ServerResponse> update(final ServerRequest serverRequest) {
    final Long id = Long.valueOf(serverRequest.pathVariable("id"));
    return serverRequest.bodyToMono(Person.class)
            .flatMap(personUpdate -> personService.update(id, personUpdate))
            .flatMap(personEntity -> ok().body(fromObject(personEntity)))
            .switchIfEmpty(Mono.defer(() -> notFound().build()))
            .onErrorMap(ValidationException.class, BadRequestException::new);
  }
}
