package com.kite.scouter.global.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application/jwt/jwtSecret.properties")
@Getter
public class JwtSecretProperties {

  @Value("${jwtSecret.key}")
  private String jwtSecretKey;
}
