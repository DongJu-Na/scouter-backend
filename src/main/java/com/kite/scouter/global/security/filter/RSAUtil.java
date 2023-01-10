package com.kite.scouter.global.security.filter;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import com.kite.scouter.global.enums.ResponseCode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.kite.scouter.global.common.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RSAUtil {

  private final static String ALGORITHM_RSA = "RSA";

  private final static String SHA256 = "SHA-256";

  private final static String SHA512 = "SHA-512";

  private final static String SIGNATURE_ALGORITHM_RSA = "SHA256withRSA";


  public static byte[] getSha256(final String msg) {

    MessageDigest messageDigest = getMessageDigest(SHA256);
    messageDigest.update(msg.getBytes());

    return messageDigest.digest();
  }

  public static byte[] getSha256(final String msg,
                                 final String salt) {
    MessageDigest messageDigest = getMessageDigest(SHA256);
    messageDigest.update(salt.getBytes());

    return messageDigest.digest(msg.getBytes());
  }

  public static String getSha512(final String msg) {
    MessageDigest messageDigest = getMessageDigest(SHA512);
    messageDigest.reset();
    messageDigest.update(msg.getBytes(StandardCharsets.UTF_8));
    return String.format("%0128x", new BigInteger(1, messageDigest.digest()));
  }

  public static String bytesToHex(final byte[] bytes) {
    StringBuilder hexString = new StringBuilder(2 * bytes.length);

    for (byte b : bytes) {
      String hex = Integer.toHexString(0xff & b);
      if(hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }

    return hexString.toString();
  }

  public static String getVerificationText(final String text) {
    byte[] verificationBytes = getSha256(text);
    return bytesToHex(verificationBytes);
  }

  public static String getVerificationText(final String prefix,
                                           final String text) {
    return getVerificationText(prefix + text);
  }

  private static MessageDigest getMessageDigest(final String algorithm) {
    try {
      return MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw BadRequestException.from(ResponseCode.TD0001);
    }
  }


}
