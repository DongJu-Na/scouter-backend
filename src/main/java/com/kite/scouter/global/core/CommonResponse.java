package com.kite.scouter.global.core;

import com.kite.scouter.global.enums.ResponseCode;
import lombok.Getter;

@Getter
public class CommonResponse<T> {

  private String code;
  private String message;
  private T data;

  private CommonResponse(final String code,
                         final String message,
                         final T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static <T> CommonResponse<T> of(final String code,
                                         final String message,
                                         final T data) {
    return new CommonResponse<>(code, message, data);
  }

  private CommonResponse(final ResponseCode responseCode,
                         final T data) {
    this.code = responseCode.getCode();
    this.message = "";
    this.data = data;
  }

  public static <T> CommonResponse<T> from(final T data) {
    return new CommonResponse<>(ResponseCode.AA0000, data);
  }

  public static <T> CommonResponse<T> of(final ResponseCode responseCode,
                                         final T data) {
    if (responseCode == null) {
      return CommonResponse.from(data);
    }
    return new CommonResponse<>(responseCode, data);
  }

}
