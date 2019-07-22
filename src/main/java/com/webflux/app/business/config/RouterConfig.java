package com.webflux.app.business.config;

import com.webflux.app.business.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

  @Bean
  public RouterFunction<ServerResponse> routes(final PersonHandler personHandler) {
    return nest(path("/person").and(accept(APPLICATION_JSON_UTF8))
           .and(contentType(APPLICATION_JSON_UTF8)),
            route(
               POST("/"), personHandler::create)).andRoute(
               GET("/person_list"), request -> personHandler.findAll()).andRoute(
               DELETE("/person/{id}"), personHandler::delete).andRoute(
               PUT("/person/{id}"), personHandler::update);
  }
}
