package com.webflux.app.business.service;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.util.List;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonServiceTest {

  @Autowired
  private PersonService personService;

  @Autowired
  private PersonRepository personRepository;

  private Person findNickname(String nickname) {
    return personRepository.findByNickname(nickname);
  }

  private Person createPerson(){
    Person person  = new Person(1L,"test", "test@bol.com");
    personRepository.save(person);
    return findNickname(person.getNickname());
  }

  @Test
  @DisplayName("Should validate if Person Create")
  void createUsers() {
    Person person  = new Person(1L,"wcarvalho", "wcarvalho@bol.com");
    personService.validateCreate(person);
    Person results = personRepository.findByNickname(person.getNickname());
    assert results.getEmail() == "wcarvalho@bol.com";
    assert results.getNickname() == "wcarvalho";
  }

  @Test
  @DisplayName("Should validate if Person Update")
  void updateUsers() {
    Person person = createPerson();
    person.setNickname("max");
    person.setEmail("max@gmail.com");
    personService.validateUpdate(person.getId(), person);
    Person results = personRepository.findAllById(person.getId());
    assert results.getNickname() == "max";
    assert results.getEmail() == "max@gmail.com";
  }

  @Test
  @DisplayName("Should validate if Person Delete")
  void deleteUsers() {
    Person person = createPerson();
    personService.validateDelete(person.getId());
    assert personRepository.findAllById(person.getId()) == null;
  }
}
