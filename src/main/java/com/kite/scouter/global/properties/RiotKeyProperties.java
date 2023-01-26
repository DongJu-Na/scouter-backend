package com.kite.scouter.global.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application/riotKey/riotKey.properties")
@Getter
public class RiotKeyProperties {

  @Value("${Riot.key}")
  private String riotKey;
}
