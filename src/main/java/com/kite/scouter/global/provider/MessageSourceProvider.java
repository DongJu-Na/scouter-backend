package com.kite.scouter.global.provider;

import org.springframework.context.MessageSourceResolvable;

public class MessageSourceProvider implements MessageSourceResolvable {

  private final String[] codes;

  private final Object[] arguments;

  private final String defaultMessage;

  private MessageSourceProvider(final String[] codes,
                                final Object[] arguments,
                                final String defaultMessage) {
    this.codes = codes;
    this.arguments = arguments;
    this.defaultMessage = defaultMessage;
  }

  public static MessageSourceProvider of (final String code) {
    return new MessageSourceProvider(new String[] {code}, null, null);
  }

  public static MessageSourceProvider of (final String code,
                                          final Object object) {
    return new MessageSourceProvider(new String[] {code}, new Object[] {object}, null);
  }

  public static MessageSourceProvider of (final String code,
                                          final Object... objects) {
    return new MessageSourceProvider(new String[] {code}, objects, null);
  }

  public static MessageSourceProvider of (final MessageSourceResolvable resolvable) {
    return new MessageSourceProvider(resolvable.getCodes(), resolvable.getArguments(), resolvable.getDefaultMessage());
  }

  @Override
  public String[] getCodes() {
    return this.codes;
  }

  @Override
  public Object[] getArguments() {
    return this.arguments;
  }

  @Override
  public String getDefaultMessage() {
    return this.defaultMessage;
  }

}
