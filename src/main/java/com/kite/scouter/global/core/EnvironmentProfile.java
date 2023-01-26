package com.kite.scouter.global.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnvironmentProfile {

  PRODUCTION("prod", "운영"),
  DEVELOPMENT("dev", "개발"),
  JUNIT("junit", "테스트"),
  LOCAL("local", "로컬"),
  ;

  private String code;
  private String desc;

  public static EnvironmentProfile getEnvironmentProfileByCode(final String code) {
    switch (code) {
      case "dev":
        return EnvironmentProfile.DEVELOPMENT;
      case "prod":
        return EnvironmentProfile.PRODUCTION;
      case "junit":
        return EnvironmentProfile.JUNIT;
      default:
        return EnvironmentProfile.LOCAL;
    }
  }

  public static boolean isEnvironmentProperties(final String activeProfile) {

    EnvironmentProfile environmentProfile = getEnvironmentProfileByActiveProfile(activeProfile);

    switch (environmentProfile) {
      case DEVELOPMENT: case PRODUCTION:
        return true;
      default:
        return false;
    }
  }

  public static boolean isEnvironmentJunitByActiveProfile(final String activeProfile) {

    EnvironmentProfile environmentProfile = getEnvironmentProfileByActiveProfile(activeProfile);

    return EnvironmentProfile.JUNIT.equals(environmentProfile);
  }

  public static boolean isEnvironmentProductionByActiveProfile(final String activeProfile) {

    EnvironmentProfile environmentProfile = getEnvironmentProfileByActiveProfile(activeProfile);

    return EnvironmentProfile.PRODUCTION.equals(environmentProfile);
  }

  private static EnvironmentProfile getEnvironmentProfileByActiveProfile(final String activeProfile) {
    return EnvironmentProfile.getEnvironmentProfileByCode(activeProfile);
  }

}
