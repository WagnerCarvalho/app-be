package com.webflux.app.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {

  public BadRequestException(final Throwable e) {
    super(HttpStatus.BAD_REQUEST, e.getMessage(), e);
  }
}
