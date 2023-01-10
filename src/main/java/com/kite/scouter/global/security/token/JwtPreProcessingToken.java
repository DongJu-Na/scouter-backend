package com.kite.scouter.global.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtPreProcessingToken extends UsernamePasswordAuthenticationToken {

  private JwtPreProcessingToken(final Object principal,
                                final Object credentials) {
    super(principal, credentials);
  }

  public JwtPreProcessingToken(final String token) {
    this(token, token.length());
  }

}
