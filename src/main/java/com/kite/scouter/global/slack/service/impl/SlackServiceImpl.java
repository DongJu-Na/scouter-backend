package com.kite.scouter.global.slack.service.impl;

import com.kite.scouter.global.core.EnvironmentProfile;
import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.properties.EnvironmentProperties;
import com.kite.scouter.global.slack.props.SlackProps;
import com.kite.scouter.global.slack.service.SlackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import org.apache.commons.lang3.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackServiceImpl implements SlackService {

  private final SlackProps slackProps;
  private final EnvironmentProperties environmentProperties;
  private final WebClient webClient;

  @Override
  public void sendSlackNotification(
      final Exception exception,
      final String msg,
      final ResponseCode responseCode,
      final HttpServletRequest request
  ) {
    if (EnvironmentProfile.isEnvironmentProductionByActiveProfile(environmentProperties.getActiveProfile())) {

      String message = """
      {
        "text" : "%s : %s (%s)\\n-API : %s\\n-remoteIP: %s\\n-serverIP: %s"
      }
       """.formatted(
          exception.getClass().getSimpleName(),
          msg.replace("\"","'"),
          responseCode.getTitle(),
          request.getRequestURL(),
          request.getRemoteAddr(),
          request.getLocalAddr()
      );

      webClient.post().uri(slackProps.getSlackHookUrl())
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(message))
        .retrieve()
        .bodyToMono(String.class)
        .block();
    }
  }

}
