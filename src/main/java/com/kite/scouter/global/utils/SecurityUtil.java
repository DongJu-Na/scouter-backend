package com.kite.scouter.global.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

  private static Authentication getTokenPayload() {
    return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication());
  }

  private static ZonedDateTime getUtcZonedDateTime() {
    return ZonedDateTime.now(ZoneId.of("UTC"));
  }

  public static Date getDateFromUtcZonedDateTime(final Long minutes) {
    return Date.from(getUtcZonedDateTime().plusMinutes(minutes).toInstant());
  }

  public static Date getDateFromUtcZonedDateTime() {
    return getDateFromUtcZonedDateTime(0L);
  }

}
