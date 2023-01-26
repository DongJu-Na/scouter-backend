package com.kite.scouter.global.exception;

import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.utils.MsgSourceUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
@EqualsAndHashCode(callSuper = false)
public class InvalidJwtException extends AuthenticationException {

  private ResponseCode responseCode;

  private InvalidJwtException(final ResponseCode responseCode,
      final String message) {
    super(message);
    this.responseCode = responseCode;
  }

  public static InvalidJwtException of(final ResponseCode responseCode,
      final String code) {
    return new InvalidJwtException(responseCode, MsgSourceUtil.getMsg(code));
  }
}
