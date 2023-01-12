package com.kite.scouter.global.enums;

import com.kite.scouter.global.core.EnumMapperType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LOLBaseUrl implements EnumMapperType {

  KR("https://kr.api.riotgames.com"),
  ASIA("https://asia.api.riotgames.com")
  ;

  private String title;

  @Override
  public String getCode() {
    return name();
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public String getValue() {
    return null;
  }
}
