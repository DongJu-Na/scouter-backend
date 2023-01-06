package com.kite.scouter.global.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EnvironmentProperties {

  @Value("${spring.profiles.active}")
  private String activeProfile;

}
