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


}
