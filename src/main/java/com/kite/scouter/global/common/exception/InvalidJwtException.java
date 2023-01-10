package com.kite.scouter.global.common.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {

  public InvalidJwtException(final String message) {
    super(message);
  }

}
