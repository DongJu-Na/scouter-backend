package com.kite.scouter.global.enums;

import com.kite.scouter.global.core.EnumMapperType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginType implements EnumMapperType {

  KAKAO("카카오"),
  NAVER("네이버"),
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
