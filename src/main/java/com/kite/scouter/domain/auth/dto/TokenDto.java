package com.kite.scouter.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenDto {

  @JsonProperty(value = "access_token")
  private String accessToken;

  @JsonProperty(value = "refresh_token")
  private String refreshToken;

  private Long accessTokenExpirationTime;

  private TokenDto(final String accessToken,
                   final String refreshToken,
                   final Long accessTokenExpirationTime) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessTokenExpirationTime = accessTokenExpirationTime;
  }

  public static TokenDto of(final String accessToken,
                            final String refreshToken) {
    return new TokenDto(accessToken, refreshToken, null);
  }

  public static TokenDto of(final String accessToken,
                            final String refreshToken,
                            final Long accessTokenExpirationTime) {
    return new TokenDto(accessToken, refreshToken, accessTokenExpirationTime);
  }

}
