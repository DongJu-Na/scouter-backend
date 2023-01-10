package com.kite.scouter.global.enums;

import com.kite.scouter.global.core.EnumMapperType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode implements EnumMapperType {

  AA0000("Success"),
  ZZ9999("Fail"),

  CI0001("Bad Parameter"),

  TY0001("Expired Access Token"),
  TY0002("Invalid Access Token"),
  TY0003("Expired Refresh Token"),
  TY0004("Invalid Refresh Token"),
  TY0005("Invalid Parameter - Token"),

  VC0001("Bad Request - AppVersion"),

  UY0001("Cannot Find User"),
  UY0002("Invalid Parameter - User"),
  UY0003("Invalid Seed"),
  UY0004("Invalid User PublicKey"),
  UY0005("Invalid Signature Image"),
  UY0007("Cannot Find User PublicKey By Parameter"),
  UY0008("Wrong Pin Code Input"),
  UY0009("Pin Code Input Count exceed the limit"),
  UY0010("Locked User"),
  UY0011("Cannot Find BiometricAuthentication"),
  UY0012("Invalid BiometricAuthentication"),
  UY0013("Cannot Find PinCode"),
  UY0014("Same With Current PinCode"),
  UY0015("Suspended User"),
  UY0016("Unauthorized User"),
  UY0017("Self Authentication Failed"),

  EM0001("Encryption Fail"),
  EM0002("Decryption Fail"),
  EM0003("Key Decoding Fail"),
  EM0004("Digital Signature Fail"),
  EM0005("Digital Signature Verification Fail"),

  IN0001("Common IO Exception"),

  TD0001("External Service Error"),

  UA0001("Bad Request - Upload"),
  UA0002("Bad File Extension"),

  VA0001("Bad Request - VisitConsult"),

  SX0001("Bad Request - Store"),

  SV0001("Bad Request - StorePos"),

  AO0001("Bad Request - Authorization"),

  FI0001("Bad Request - FAQ"),

  EJ0001("Bad Request - Event"),
  EJ0002("Bad Request - No Event Participation Chance"),
  EJ0003("Event Join User Not Exists"),
  EJ0004("Event Item Not Exists"),
  EJ0005("Event Items Are Out Of Stock"),
  EJ0006("Event Target User Not Exists"),

  NZ0001("Bad Request - Notification"),

  DL0001("Bad Request - Document"),

  EI0001("Bad Request - Enum"),

  NT0001("Bad Request - Notice"),

  AF0001("Bad Request - Account"),
  AF0002("Unavailable Password"),
  AF0003("Account Not Exists"),
  AF0004("Locked Account"),
  AF0005("Incorrect Password"),
  AF0006("Inaccessible Account"),
  AF0007("Bad Request - AccountMask"),

  CG0001("Bad Request - Card"),
  CG0002("Bad Request - Card And User Not Matching"),
  CG0003("Bad Request - Nice EPay API Call Error"),
  CG0004("Bad Request - Nice EPay API Response Empty"),

  TM0001("Bad Request - Term"),

  CU0001("Bad Request - Coupon"),

  TA0001("Bad Request - Transaction Otc"),
  TA0002("Bad Request - Otc Valid Type Enum"),
  TA0003("Bad Request - Transaction Receiver Result Code Empty"),
  TA0004("Bad Request - Transaction Receiver Approval Otc Unused Empty"),
  TA0005("Bad Request - Transaction Receiver App User Not Found"),
  TA0006("Bad Request - Transaction Otc Empty By Transaction"),
  TA0007("Bad Request - Transaction Otc Create Fail"),
  TA0008("Bad Request - Transaction EPay Result Code Empty"),
  TA0009("Bad Request - Transaction Otc Not Found By Transaction Code"),

  OD0001("Bad Request - Ori Order Empty"),
  OD0002("Bad Request - Order"),

  OS0001("Bad Request - Old Server Request Error (Get UserNumber)"),

  CF0001("Bad Request - Category"),

  BG0001("Bad Request - Brand"),

  UN0001("Bad Request - Device Type Header Empty"),
  UN0002("Bad Request - App Service Status Main Expose Is True"),

  EK0001("Bad Request - Easter Egg Not Running")
  ;

  private String title;

  @Override
  public String getCode() {
    return name();
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public String getValue() {
    return null;
  }

}
