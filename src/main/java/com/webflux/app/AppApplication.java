package com.webflux.app;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.webflux.app.business.domain")
@EnableJpaRepositories("com.webflux.app.business.repository")
public class AppApplication {

  public static void main(final String[] args) {
    SpringApplication.run(AppApplication.class, args);
  }

  @Bean
  CommandLineRunner start(final PersonRepository personRepository) {
    return args -> {
      Person person  = new Person(1L,"wcarvalho", "wcarvalhoti@gmail.com");
      personRepository.save(person);
      personRepository.findAll();
    };
  }

}