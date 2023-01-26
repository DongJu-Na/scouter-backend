package com.kite.scouter.domain.auth.service;

import com.kite.scouter.domain.auth.dto.AuthenticationRequest;
import com.kite.scouter.domain.auth.dto.RegisterRequest;
import com.kite.scouter.domain.auth.dto.TokenDto;
import com.kite.scouter.domain.auth.dto.UserContext;
import com.kite.scouter.domain.user.model.User;
import com.kite.scouter.domain.user.repository.UserRepository;
import com.kite.scouter.global.exception.BadRequestException;
import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.enums.Role;
import com.kite.scouter.global.exception.InvalidLoginException;
import com.kite.scouter.global.properties.JwtProperties;
import com.kite.scouter.global.security.jwt.JwtService;
import com.kite.scouter.global.utils.SecurityUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final JwtProperties jwtProperties;

  public TokenDto register(RegisterRequest request) {

    if(!userRepository.findByEmail(request.getEmail()).isEmpty()){
      throw BadRequestException.of(ResponseCode.UY0003, "user.email.duplication");
    }

    if(!userRepository.findByNickName(request.getNickName()).isEmpty()){
      throw BadRequestException.of(ResponseCode.UY0004, "user.nickName.duplication");
    }

    var user = User.builder()
        .nickName(request.getNickName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    userRepository.save(user);

    UserContext userContext = UserContext.of(
        user.getEmail(),
        user.getNickName()
    );

    return TokenDto.of(
        jwtService.generateToken(userContext),
        jwtService.generateRefreshToken(userContext),
        SecurityUtil.getDateFromUtcZonedDateTime(jwtProperties.getExpireMinutes()).getTime()
    );
  }

  public TokenDto authenticate(AuthenticationRequest request) {

    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> BadRequestException.of(ResponseCode.UY0001, "user.email.not.find"));

    UserContext userContext = UserContext.of(
        user.getEmail(),
        user.getNickName()
    );

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );

    return TokenDto.of(
        jwtService.generateToken(userContext),
        jwtService.generateRefreshToken(userContext),
        SecurityUtil.getDateFromUtcZonedDateTime(jwtProperties.getExpireMinutes()).getTime()
    );
  }

  public TokenDto authenticateRefresh(final HttpServletRequest request) {
    String jwt = jwtService.getJWTofAuthorization(request);
    UserContext userContext = jwtService.decodeJwt(jwt);
    return TokenDto.of(
        jwtService.generateToken(userContext),
        jwtService.generateRefreshToken(userContext),
        SecurityUtil.getDateFromUtcZonedDateTime(jwtProperties.getExpireMinutes()).getTime()
    );
  }
}
