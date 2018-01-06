package com.example.rprev.vcam;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ravy on 2018/01/05.
 */

public class CoinCheckAccessor {

    public static void setHeader(HttpResponseAsync target, String url,String accessKey, String secretKey) {
        final String ALGORISM = "hmacSHA256";

        long nonce=System.currentTimeMillis();
        String message = String.valueOf(nonce) + url;


        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORISM);
        String signature_s="";

        try {
            Mac mac = Mac.getInstance(ALGORISM);
            mac.init(secretKeySpec);
            byte[] signature = mac.doFinal(message.getBytes());
            signature_s=byteToString(signature);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        target.addHeader("ACCESS-KEY",accessKey);
        target.addHeader("ACCESS-NONCE", String.valueOf(nonce));
        target.addHeader("ACCESS-SIGNATURE",signature_s);

    }

    private static String byteToString(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            int d = b[i];
            d += (d < 0)? 256 : 0;
            if (d < 16) {
                buffer.append("0");
            }
            buffer.append(Integer.toString(d, 16));
        }
        return buffer.toString();
    }

}
