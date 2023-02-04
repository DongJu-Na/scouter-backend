package com.kite.scouter.global.utils;

import com.kite.scouter.domain.auth.dto.UserContext;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

  private static Authentication getTokenPayload() {
    return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication());
  }

  private static String getAuthenticationToUser(final Authentication authentication) {
    return ((UserDetails) authentication.getPrincipal()).getUsername();
  }

  public static String getUserEmail() {
    return getAuthenticationToUser(getTokenPayload());
  }


  private static ZonedDateTime getCommonZonedDateTime() {
    return ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
  }

  public static Date getDateFromCommonZonedDateTime(final Long minutes) {
    return Date.from(getCommonZonedDateTime().plusMinutes(minutes).toInstant());
  }

  public static Date getDateFromCommonZonedDateTime() {
    return getDateFromCommonZonedDateTime(0L);
  }

}
