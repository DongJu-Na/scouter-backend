package com.kite.scouter.global.core;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Getter
@AllArgsConstructor
public enum CatchRequestMatcher {

  REFRESH_TOKEN(POST, "/api/v1/user/refresh", "Refresh Token"),
  BOARD_POST(POST,"/api/v1/boards","Boards Create"),
  BOARD_PUT(PUT,"/api/v1/boards","Boards Put"),
  BOARD_DELETE(DELETE,"/api/v1/boards","Boards Delete")
  ;

  private HttpMethod method;
  private String url;
  private String description;

  public static List<RequestMatcher> getSkipRequestMatchers() {
    return Arrays.stream(CatchRequestMatcher.values())
      .map(CatchRequestMatcher::getSkipAntPathRequestMatcher).collect(Collectors.toList());
  }

  public static AntPathRequestMatcher getSkipAntPathRequestMatcher(final CatchRequestMatcher catchRequestMatcher) {
    return new AntPathRequestMatcher(catchRequestMatcher.getUrl(), catchRequestMatcher.method.name());
  }

}
