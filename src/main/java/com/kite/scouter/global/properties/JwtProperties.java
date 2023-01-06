package com.kite.scouter.global.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application/jwt/jwt.properties")
@Getter
public class JwtProperties {

  @Value("${jwt.expire.minutes}")
  private Long expireMinutes;

  @Value("${jwt.refresh.expire.minutes}")
  private Long refreshExpireMinutes;

  @Value("${jwt.app.expire.minutes}")
  private Long appExpireMinutes;

  @Value("${jwt.app.refresh.expire.minutes}")
  private Long appRefreshExpireMinutes;

}
