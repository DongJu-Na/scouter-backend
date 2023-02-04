package com.kite.scouter.global.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeUtil {

  public static boolean isBefore(LocalDateTime localDateTime) {
    return getLocalDateTime().isBefore(localDateTime);
  }

  public static LocalDateTime getLocalDateTime() {
    return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
  }

}
