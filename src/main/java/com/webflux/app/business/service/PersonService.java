package com.webflux.app.business.service;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.exception.ValidationException;
import com.webflux.app.business.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import javax.transaction.Transactional;
import java.util.Optional;
import static reactor.core.publisher.Mono.fromCallable;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;
  private Logger log = LoggerFactory.getLogger(this.getClass());

  public Person validateCreate(final Person person) {
    final Person persistedPersonEntity = personRepository.findFirstByEmail(person.getEmail());
    if (persistedPersonEntity == null) {
      return createPerson(person);
    } else {
      throw new ValidationException("user already registered");
    }
  }

  private Person createPerson(final Person person) {
    return personRepository.save(person);
  }

  public Mono<Person> save(final Person person) {
    return fromCallable(() -> validateCreate(person))
            .publishOn(Schedulers.elastic())
            .flatMap(Mono::justOrEmpty)
            .doOnError(e -> log.error("user already registered", e));
  }

  public Mono<Person> delete(final Long id) {
    return fromCallable(() -> validateDelete(id))
            .publishOn(Schedulers.elastic())
            .flatMap(Mono::justOrEmpty)
            .doOnError(e -> log.error("user not registered", e));
  }

  private void deletePerson(final Long id) {
    personRepository.deleteById(id);
  }

  public Optional<Person> validateDelete(final Long id) {
    final Optional<Person> deletePersonEntity = personRepository.findById(id);
    if (deletePersonEntity.isPresent()) {
      deletePerson(id);
    } else {
      throw new ValidationException("user not registered");
    }
    return deletePersonEntity;
  }

  @Transactional
  public Mono<Person> update(final Long id, final Person person) {
    return fromCallable(() -> validateUpdate(id, person))
            .publishOn(Schedulers.elastic())
            .flatMap(Mono::justOrEmpty)
            .doOnError(e -> log.error("user already registered", e));
  }
  
  private void updateAs(final Person person) {
    personRepository.save(person);
  }

  public Person validateUpdate(final Long id, final Person updatePerson) {

    final Optional<Person> updatePersonEntity = personRepository.findById(id);
    if (updatePersonEntity.isPresent()) {
      updatePerson.setId(id);
      updatePerson.setNickname(updatePerson.getNickname() == null ? updatePersonEntity.get().getNickname() :
              updatePerson.getNickname());

      updatePerson.setEmail(updatePerson.getEmail() == null ? updatePersonEntity.get().getEmail() :
              updatePerson.getEmail());

      updateAs(updatePerson);
    } else {
      throw new ValidationException("user not registered");
    }
    return updatePerson;
  }
}
