package com.kite.scouter.global.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidLoginException extends AuthenticationException {

  public InvalidLoginException(final String message) {
    super(message);
  }

}
