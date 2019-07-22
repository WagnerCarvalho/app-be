package com.webflux.app.core.health;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class HealthRouter {
  @Bean
  RouterFunction<ServerResponse> pingRoutes() {
    return route(GET("/ping"), x -> {
      return ok().syncBody("pong");
    });
  }
}
