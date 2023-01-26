package com.kite.scouter.global.security.jwt;


import com.kite.scouter.domain.auth.dto.UserContext;
import com.kite.scouter.global.properties.JwtProperties;
import com.kite.scouter.global.properties.JwtSecretProperties;
import com.kite.scouter.global.utils.SecurityUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtSecretProperties jwtSecretProperties;

  private final JwtProperties jwtProperties;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(final UserContext userContext) {
    return generateToken(userContext, SecurityUtil.getDateFromUtcZonedDateTime(jwtProperties.getExpireMinutes()));
  }

  public String generateRefreshToken(final UserContext userContext) {
    return generateToken(userContext, SecurityUtil.getDateFromUtcZonedDateTime(jwtProperties.getRefreshExpireMinutes()));
  }

  public String generateToken(
      final UserContext userContext,
      final Date expire
  ) {
    return Jwts
        .builder()
        .setSubject(userContext.getEmail())
        .claim("NICK_NAME",userContext.getNickName())
        .setIssuedAt(SecurityUtil.getDateFromUtcZonedDateTime())
        .setExpiration(expire)
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }


  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(SecurityUtil.getDateFromUtcZonedDateTime());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecretProperties.getJwtSecretKey());
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public UserContext decodeJwt(final String token) {
    Jwt decodedJWT = jwtDecoder().decode(token);
    return UserContext.of(
        decodedJWT.getSubject(),
        decodedJWT.getClaim("NICK_NAME").toString()
    );
  }

  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(Decoders.BASE64.decode(jwtSecretProperties.getJwtSecretKey()), SignatureAlgorithm.HS256.getValue()))
        .build();
  }

  public String getJWTofAuthorization(final HttpServletRequest request) {
    return request.getHeader("Authorization").substring(7);
  }
}
