package com.kite.scouter.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.kite.scouter.global.core.CommonResponse;
import com.kite.scouter.global.core.EnvironmentProfile;
import com.kite.scouter.global.core.ErrorData;
import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.properties.EnvironmentProperties;
import com.kite.scouter.global.slack.service.SlackService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

  private final SlackService slackService;

  private final EnvironmentProperties environmentProperties;

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(BAD_REQUEST)
  public CommonResponse<ErrorData> handleNullPointerException(
      final NullPointerException exception,
      final HttpServletRequest request
  ) {
    String errorWhich = exception.getStackTrace()[0] + "\n" + exception.getStackTrace()[1] + "\n" + exception.getStackTrace()[2];
    ErrorResponse errorResponse = ErrorResponse.of("common.bad.null.pointer.exception", errorWhich);
    log.info("[Error NullPointException] : {}", errorResponse.toString());

    checkEnvironmentPropertiesAfterSlackNotification(exception, exception.getMessage(), ResponseCode.IN0002, request);

    return CommonResponse.of(ResponseCode.IN0002.getCode(),exception.getLocalizedMessage(), ErrorData.of());
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(BAD_REQUEST)
  public CommonResponse<ErrorData> handleBadRequestException(
      final BadRequestException exception,
      final HttpServletRequest request
  ) {
    checkEnvironmentPropertiesAfterSlackNotification(exception, exception.getLocalizedMessage(), exception.getResponseCode(), request);
    return CommonResponse.of(exception.getResponseCode().getCode(),exception.getLocalizedMessage(), ErrorData.of());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(BAD_REQUEST)
  public CommonResponse<ErrorData> handleHttpMessageNotReadableException() {
    return CommonResponse.of(ResponseCode.CI0002.getCode(), ResponseCode.CI0002.getTitle(), ErrorData.of());
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(BAD_REQUEST)
  public CommonResponse<ErrorData> handleBindException(final BindException exception) {
    return CommonResponse.of(ResponseCode.CI0001.getCode(), ResponseCode.CI0001.getTitle(), ErrorData.of());
  }

  @ExceptionHandler(ServletRequestBindingException.class)
  @ResponseStatus(BAD_REQUEST)
  public CommonResponse<ErrorData> handleServletRequestBindingException(final ServletRequestBindingException exception) {
    return CommonResponse.of(ResponseCode.CI0001.getCode(), ResponseCode.CI0001.getTitle(), ErrorData.of());
  }

  @ExceptionHandler(TypeMismatchException.class)
  @ResponseStatus(BAD_REQUEST)
  public CommonResponse<ErrorData> handleTypeMismatchException(final TypeMismatchException exception) {
    return CommonResponse.of(ResponseCode.CI0001.getCode(), "TypeMismatchException", ErrorData.of());
  }


  @ExceptionHandler(IOException.class)
  @ResponseStatus(BAD_REQUEST)
  public CommonResponse<ErrorData> handleIOException(
      final IOException exception,
      final HttpServletRequest request
  ) {
    checkEnvironmentPropertiesAfterSlackNotification(exception, exception.getLocalizedMessage(), ResponseCode.IN0001, request);
    return CommonResponse.of(ResponseCode.IN0001.getCode(), ResponseCode.IN0001.getTitle(), ErrorData.of());
  }

  @ExceptionHandler(InvalidJwtException.class)
  @ResponseStatus(UNAUTHORIZED)
  public CommonResponse<ErrorData> handleInvalidJwtException(
      final InvalidJwtException exception,
      final HttpServletRequest request
  ) {
    checkEnvironmentPropertiesAfterSlackNotification(exception, exception.getLocalizedMessage(), exception.getResponseCode(), request);
    return CommonResponse.of(exception.getResponseCode().getCode(), exception.getMessage(), ErrorData.of());
  }

  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(FORBIDDEN)
  public CommonResponse<ErrorData> handleForbiddenException(final ForbiddenException exception) {
    return CommonResponse.of(exception.getResponseCode().getCode(), exception.getMessage(), ErrorData.of());
  }

  private void checkEnvironmentPropertiesAfterSlackNotification(
      final Exception exception,
      final String msg,
      final ResponseCode responseCode,
      final HttpServletRequest request
  ) {
    if (EnvironmentProfile.isEnvironmentProductionByActiveProfile(environmentProperties.getActiveProfile())) {
      slackService.sendSlackNotification(exception, msg, responseCode, request);
    }
  }

}