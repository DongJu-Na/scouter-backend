package com.kite.scouter.domain.auth.controller;

import com.kite.scouter.domain.auth.dto.AuthenticationRequest;
import com.kite.scouter.domain.auth.dto.TokenDto;
import com.kite.scouter.domain.auth.service.AuthenticationService;
import com.kite.scouter.domain.auth.dto.RegisterRequest;
import com.kite.scouter.global.core.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  @ResponseStatus(CREATED)
  public CommonResponse<TokenDto> register(@RequestBody @Valid RegisterRequest request) {
    return CommonResponse.from(authenticationService.register(request));
  }

  @PostMapping("/authenticate")
  @ResponseStatus(CREATED)
  public CommonResponse<TokenDto> authenticate(@RequestBody @Valid AuthenticationRequest request) {
    return CommonResponse.from(authenticationService.authenticate(request));
  }

}
