package com.webflux.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.webflux.app.business.domain")
@EnableJpaRepositories("com.webflux.app.business.repository")
public class AppApplication {

  public static void main(final String[] args) {
    SpringApplication.run(AppApplication.class, args);
  }

}