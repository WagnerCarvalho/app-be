package com.webflux.app.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

  private String logMessage;

  public ValidationException(final String logMessage) {
    super(logMessage);
    this.logMessage = logMessage;
  }

}
