package com.kite.scouter.global.utils;

import java.util.Random;

public class CommonUtil {

  public static String getRandomSalt() {

    Random random = new Random();

    byte[] salt = new byte[16];

    random.nextBytes(salt);

    StringBuilder stringBuilder = new StringBuilder();

    for (byte b : salt) {
      stringBuilder.append(String.format("%02x", b));
    }

    return stringBuilder.toString();
  }

}
