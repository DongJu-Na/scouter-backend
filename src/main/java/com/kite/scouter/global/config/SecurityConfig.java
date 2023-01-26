package com.kite.scouter.global.config;

import com.kite.scouter.global.core.CatchRequestMatcher;
import com.kite.scouter.global.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .securityMatchers((matcher) -> CatchRequestMatcher.getSkipRequestMatchers())
        //.authorizeHttpRequests()
        //.requestMatchers(CatchRequestMatcher.getSkipAntPathRequestMatcher(CatchRequestMatcher.REFRESH_TOKEN))
        //.authorizeHttpRequests((match) -> match.requestMatchers("/api/lol/**").permitAll())
        //.requestMatchers("/api/v1/auth/**","/api/lol/**","/docs/**", "/api/v1/boards**", "/api/v1/boards/**")
        //.permitAll()
        //.anyRequest()
        //.authenticated()
        //.and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
