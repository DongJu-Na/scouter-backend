package com.kite.scouter.global.utils;

import java.util.List;
import org.springframework.util.ObjectUtils;

public class ObjectUtil {

  public static <T> boolean isEmpty(T data) {
    return ObjectUtils.isEmpty(data);
  }

  public static <T> boolean isEmpty(List<T> data) {
    return ObjectUtils.isEmpty(data);
  }

  public static String splitAndLast(final String text,
                                    final String regex) {
    String[] array = text.split(regex);
    return array[array.length - 1];
  }

}
