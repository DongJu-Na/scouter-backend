package com.kite.scouter.global.enums;

import com.kite.scouter.global.core.EnumMapperType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode implements EnumMapperType {

  AA0000("Success"),
  ZZ9999("Fail"),

  CI0001("Bad Parameter"),
  CI0002("Null Request"),

  TY0001("Expired Access Token"),
  TY0002("Invalid Access Token"),
  TY0003("Expired Refresh Token"),
  TY0004("Invalid Refresh Token"),
  TY0005("Invalid Parameter - Token"),

  UY0001("Cannot Find User"),
  UY0002("Cannot Find NickName"),
  UY0003("Duplication User"),
  UY0004("Duplication NickName"),

  BY0001("Cannot Find Board"),

  RY0001("cannot FInd Reply"),

  CY0001("Cannot Find Category"),

  IN0001("Common IO Exception"),
  IN0002("NullPoint Exception");
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
