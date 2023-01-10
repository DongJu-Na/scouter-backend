package com.kite.scouter.global.enums;

import java.util.Arrays;
import java.util.NoSuchElementException;

import com.kite.scouter.global.core.EnumMapperType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role implements EnumMapperType {

  USER("ROLE_USER", "일반 사용자"),
  ADMIN("ROLE_ADMIN", "관리자"),
  ;

  private String title;
  private String description;

  public boolean isCorrectName(final String name) {
    return name.equalsIgnoreCase(this.title);
  }

  public static Role getRoleByName(final String roleName) {
    return Arrays.stream(Role.values())
      .filter(value -> value.isCorrectName(roleName))
      .findFirst()
      .orElseThrow(() -> new NoSuchElementException("검색된 권한이 없습니다."));
  }

  public static Role getRoleNullableByName(final String roleName) {
    return Arrays.stream(Role.values())
      .filter(value -> value.isCorrectName(roleName))
      .findFirst()
      .orElse(null);
  }

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
