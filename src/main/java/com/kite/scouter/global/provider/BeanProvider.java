package com.kite.scouter.global.provider;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.wildfly.common.annotation.NotNull;

@Component
public class BeanProvider implements ApplicationContextAware {

  private static ApplicationContext context;

  @Override
  public void setApplicationContext(@NotNull final ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public static <T> T getBean(Class<T> beanClass) {
    return context.getBean(beanClass);
  }

}
