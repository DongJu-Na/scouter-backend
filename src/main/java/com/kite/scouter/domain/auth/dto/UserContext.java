package com.kite.scouter.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserContext {

  private String email;

  private String nickName;

  private UserContext(final String email,
                      final String nickName) {
    this.email = email;
    this.nickName = nickName;
  }

  public static UserContext of(final String email,
                               final String nickName) {
    return new UserContext(email, nickName);
  }

}
