package com.kite.scouter.global.slack.props;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application/slack/slack.properties")
@Getter
public class SlackProps {

  @Value("${slack.hook.url}")
  private String slackHookUrl;
}
