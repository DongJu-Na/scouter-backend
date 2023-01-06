package com.kite.scouter.global.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

  public static String getEncryptSha256(final String text) throws NoSuchAlgorithmException {

    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    messageDigest.update(text.getBytes());

    return bytesToHex(messageDigest.digest());
  }

  private static String bytesToHex(final byte[] bytes) {

    StringBuilder builder = new StringBuilder();
    for (byte b : bytes) {
      builder.append(String.format("%02x", b));
    }
    return builder.toString();
  }

}
