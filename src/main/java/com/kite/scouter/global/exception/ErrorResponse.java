package com.kite.scouter.global.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ErrorResponse {
  private String code;
  private String message;

  private ErrorResponse(final String code,
                        final String message) {
    this.code = code;
    this.message = message;
  }

  public static ErrorResponse of(final String code,
                                 final String message) {
    return new ErrorResponse(code, message);
  }

}
