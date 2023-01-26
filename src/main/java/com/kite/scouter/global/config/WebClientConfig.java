package com.kite.scouter.global.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kite.scouter.global.properties.RiotKeyProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Component
@RequiredArgsConstructor
public class WebClientConfig {

  private final RiotKeyProperties riotKeyProperties;

  public final ObjectMapper OM = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

  @Bean
  public WebClient commonWebClient(ExchangeStrategies exchangeStrategies, HttpClient httpClient) {
    return WebClient
        .builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .defaultHeader(HttpHeaders.ORIGIN,"https://developer.riotgames.com")
        .defaultHeader("X-Riot-Token", riotKeyProperties.getRiotKey())
        .defaultHeader(HttpHeaders.ACCEPT_CHARSET,"application/x-www-form-urlencoded; charset=UTF-8")
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .exchangeStrategies(exchangeStrategies)
        .build();
  }

  @Bean
  public HttpClient defaultHttpClient(ConnectionProvider provider) {

    return HttpClient.create(provider)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5_000)
        .doOnConnected(conn ->
            conn.addHandlerLast(new ReadTimeoutHandler(5)) //읽기시간초과 타임아웃
                .addHandlerLast(new WriteTimeoutHandler(5)));
  }

  @Bean
  public ConnectionProvider connectionProvider() {

    return ConnectionProvider.builder("http-pool")
        .maxConnections(100)					     // connection pool의 갯수
        .pendingAcquireTimeout(Duration.ofMillis(0)) //커넥션 풀에서 커넥션을 얻기 위해 기다리는 최대 시간
        .pendingAcquireMaxCount(-1) 				//커넥션 풀에서 커넥션을 가져오는 시도 횟수 (-1: no limit)
        .maxIdleTime(Duration.ofMillis(2000L)) 		//커넥션 풀에서 idle 상태의 커넥션을 유지하는 시간
        .build();
  }

  @Bean
  public ExchangeStrategies defaultExchangeStrategies() {

    return ExchangeStrategies.builder().codecs(config -> {
      config.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(OM, MediaType.APPLICATION_JSON));
      config.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(OM, MediaType.APPLICATION_JSON));
      config.defaultCodecs().maxInMemorySize(1024 * 1024); // max buffer 1MB 고정. default: 256 * 1024
    }).build();
  }
}