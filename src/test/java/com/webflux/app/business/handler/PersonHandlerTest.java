package com.webflux.app.business.handler;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonHandlerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private PersonRepository personRepository;

  private void createData(final Person person) {
    new Person(person.getId(),person.getNickname(), person.getEmail());
    personRepository.save(person);
  }

  @Test
  @DisplayName("Created Users")
  void createdUserTest() {
    Person person  = new Person(1L,"wcarvalho", "wcarvalhoti@gmail.com");
    personRepository.save(person);
    personRepository.findAll();
  }

  @Test
  @DisplayName("Should POST return OK | BAD REQUEST")
  void postTest() {
    Person person = new Person.Builder()
            .email("wcarvalhoti@gmail.com")
            .nickname("wcarvalho")
            .build();
    webTestClient.post()
            .uri("/person")
            .syncBody(person)
            .exchange()
            .expectStatus()
            .isOk();
    webTestClient.post()
            .uri("/person")
            .syncBody(person)
            .exchange()
            .expectStatus()
            .isBadRequest();
  }

  @Test
  @DisplayName("Should GET return OK")
  void getTest() {
    webTestClient.get()
            .uri("/person_list")
            .exchange()
            .expectStatus()
            .isOk();
  }

  @Test
  @DisplayName("Should DELETE return OK | BAD REQUEST")
  void deleteTest() {
    createData(new Person(1L,"new_user_1", "new_user_1@gmail.com"));
    createData(new Person(2L,"new_user_2", "new_user_2@gmail.com"));
    webTestClient.delete()
            .uri("/person/1")
            .exchange()
            .expectStatus()
            .isOk();
    webTestClient.delete().uri("/person/2")
            .exchange()
            .expectStatus()
            .isOk();
    webTestClient.delete().uri("/person/3")
            .exchange()
            .expectStatus()
            .isBadRequest();
  }

  @Test
  @DisplayName("Should PUT return BAD REQUEST | OK")
  void putTest() {
    createData(new Person(1L,"new_user_3", "new_user_3@gmail.com"));
    Person person = new Person.Builder()
            .email("teste@gmail.com")
            .nickname("aaaa")
            .build();
    webTestClient.put()
            .uri("/person/1")
            .syncBody(person)
            .exchange()
            .expectStatus()
            .isOk();
    webTestClient.delete().uri("/person/4")
            .exchange()
            .expectStatus()
            .isBadRequest();
  }
}

