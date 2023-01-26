package com.kite.scouter.global.slack.service;

import com.kite.scouter.global.enums.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;

public interface SlackService {

  void sendSlackNotification(
      Exception exception,
      String msg,
      ResponseCode responseCode,
      HttpServletRequest request
  );

}
