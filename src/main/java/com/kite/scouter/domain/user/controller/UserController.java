package com.kite.scouter.domain.user.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.kite.scouter.domain.auth.dto.AuthenticationRequest;
import com.kite.scouter.domain.auth.dto.TokenDto;
import com.kite.scouter.domain.auth.service.AuthenticationService;
import com.kite.scouter.domain.user.service.UserService;
import com.kite.scouter.global.core.CommonResponse;
import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.exception.InvalidJwtException;
import com.kite.scouter.global.utils.BindingResultUtil;
import com.kite.scouter.global.utils.ObjectUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final AuthenticationService authenticationService;

  @PostMapping("/refresh")
  @ResponseStatus(CREATED)
  public CommonResponse<TokenDto> authenticate(
      final HttpServletRequest request
  ) throws AuthenticationException {
    final String authHeader = request.getHeader("Authorization");
    if (ObjectUtil.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
      throw InvalidJwtException.of(ResponseCode.TY0005, "auth.jwt.valid.token");
    }
    return CommonResponse.from(authenticationService.authenticateRefresh(request));
  }

}
