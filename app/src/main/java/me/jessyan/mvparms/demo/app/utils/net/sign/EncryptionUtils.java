package me.jessyan.mvparms.demo.app.utils.net.sign;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密算法操作类
 * Created by tao on 2016-06-30.
 */
public class EncryptionUtils {
    public static final String KEY_SHA = "SHA-1";
    public static final String KEY_MD5 = "MD5";


    /**
     * BASE64解密
     */
    public static byte[] encodeBASE64(String key) throws Exception {
        return Base64.encode(key.getBytes(), Base64.DEFAULT);
    }

    public static byte[] decodeBASE64(String base64) throws Exception {
        return Base64.decode(base64, Base64.NO_WRAP);
    }


    /**
     * BASE64加密
     */
    public static String encodeToStringBASE64(byte[] key) throws Exception {
        return Base64.encodeToString(key, Base64.DEFAULT);
    }

    /**
     * MD5加密
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();

    }

    /**
     * MD5加密
     */
    public static String encryptMD5ToString(String data) throws RuntimeException {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("md5");
        }

        byte[] results = md5.digest(data.getBytes());
        return toHex(results);
    }

    private static String toHex(byte[] results) {
        if (results == null)
            return null;
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < results.length; i++) {
            int hi = (results[i] >> 4) & 0x0f;
            int lo = results[i] & 0x0f;
            hexString.append(Character.forDigit(hi, 16)).append(
                    Character.forDigit(lo, 16));
        }
        return hexString.toString();
    }
}
