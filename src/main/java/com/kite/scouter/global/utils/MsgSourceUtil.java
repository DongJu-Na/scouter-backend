package com.kite.scouter.global.utils;

import java.util.Locale;
import com.kite.scouter.global.provider.BeanProvider;
import com.kite.scouter.global.provider.MessageSourceProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;

public class MsgSourceUtil {

  private static MessageSource getDefaultMsgSource() {
    return BeanProvider.getBean(MessageSource.class);
  }

  public static String getMsg(final MessageSourceProvider messageSourceProvider) {
    return getDefaultMsgSource().getMessage(messageSourceProvider, Locale.KOREA);
  }

  public static String getMsg(final String code) {
    return getDefaultMsgSource().getMessage(MessageSourceProvider.of(code), Locale.KOREA);
  }

  public static String getMsg(final String code,
                              final Object object) {
    return getDefaultMsgSource().getMessage(MessageSourceProvider.of(code, object), Locale.KOREA);
  }

  public static String getMsg(final String code,
                              final Object... objects) {
    return getDefaultMsgSource().getMessage(MessageSourceProvider.of(code, objects), Locale.KOREA);
  }

  public static String getMsg(final MessageSourceResolvable resolvable) {
    return getDefaultMsgSource().getMessage(MessageSourceProvider.of(resolvable), Locale.KOREA);
  }

}
