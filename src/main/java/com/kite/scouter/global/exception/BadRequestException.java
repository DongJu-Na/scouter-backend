package com.kite.scouter.global.exception;

import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.utils.MsgSourceUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends RuntimeException {

  private ResponseCode responseCode;

  private BadRequestException(final ResponseCode responseCode) {
    super(responseCode.getTitle());
    this.responseCode = responseCode;
  }

  private BadRequestException(final ResponseCode responseCode,
                              final String message) {
    super(message);
    this.responseCode = responseCode;
  }

  public static BadRequestException from(final ResponseCode responseCode) {
    return new BadRequestException(responseCode);
  }

  public static BadRequestException of(final ResponseCode responseCode,
                                       final String code) {
    return new BadRequestException(responseCode, MsgSourceUtil.getMsg(code));
  }

  public static BadRequestException of(final ResponseCode responseCode,
                                       final String code,
                                       final Object object) {
    return new BadRequestException(responseCode, MsgSourceUtil.getMsg(code, object));
  }

  public static BadRequestException of(final ResponseCode responseCode,
                                       final String message,
                                       final Object... objects) {
    return new BadRequestException(responseCode, MsgSourceUtil.getMsg(message, objects));
  }

}
