package com.kite.scouter.global.security.jwt;

import com.kite.scouter.global.common.exception.InvalidJwtException;
import com.kite.scouter.global.utils.ObjectUtil;

import org.springframework.stereotype.Component;

@Component
public class HeaderTokenExtractor {

  public String extract(final String header) {

    String headerToken = "Bearer ";

    if (ObjectUtil.isEmpty(header) || !header.startsWith(headerToken)) {
      throw new InvalidJwtException("올바른 토큰 정보가 아닙니다.");
    }

    int headerTokenLength = headerToken.length();
    int headerLength = header.length();

    return header.substring(headerTokenLength, headerLength);
  }

}
