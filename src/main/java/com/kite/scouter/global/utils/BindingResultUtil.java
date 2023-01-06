package com.kite.scouter.global.utils;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;

public class BindingResultUtil extends BindingResultUtils {

  public static void handleBindingResult(final BindingResult bindingResult) throws BindException {

    if (bindingResult.hasErrors()) {
      throw new BindException(bindingResult);
    }
  }

}
