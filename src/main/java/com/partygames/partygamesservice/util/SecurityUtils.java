package com.partygames.partygamesservice.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SecurityUtils {

    public static String encodeHashSha256(String inputStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(inputStr.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < encodedHash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedHash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                    hexString.append(hex);
                }
            }
            return hexString.toString();
        } catch (Exception e) {
            PartyLogger.error("Unable to encode string to SHA-256");
        }
        return null;
    }
}