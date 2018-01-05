package me.jessyan.mvparms.demo.app.utils.net.sign;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * 掌沃验签相关的类
 * Created by tao on 2016-07-14.
 */
public class RSAUtils {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final int PRIVATE_KEY_LENGTH = 1024;
    //公钥
    public static final String RSA = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfpp4gBXCsMwlahzypEYMdKFRxLaQWZKPkry3T4MglyoIy0eJAsxAYnEixNgkzBWNu2fqXfxupEfq423+VcQLwgFPawvxjel8I3gTihO2saOdsTWmbaVu/zzqOBiT2xB7gJu8MW2dXPHmw9pZI8FdS2GH9wWQS8BxvWN2f8MLOlwIDAQAB";

    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map keyMap = new HashMap(2);
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
        return keyMap;
    }

    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = EncryptionUtils.decodeBASE64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateK);
        signature.update(data);
        return EncryptionUtils.encodeToStringBASE64(signature.sign());
    }

    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = EncryptionUtils.decodeBASE64(publicKey);
        System.out.println("keyBytes =" + keyBytes);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(EncryptionUtils.decodeBASE64(sign));
    }

    private static byte[] decryptByPrivateKey(byte[] encryptedData,
                                              String privateKey) throws Exception {
        byte[] keyBytes = EncryptionUtils.decodeBASE64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        int i = 0;

        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > 128)
                cache = cipher.doFinal(encryptedData, offSet, 128);
            else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 128;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    private static byte[] decryptByPublicKey(byte[] encryptedData,
                                             String publicKey) throws Exception {
        byte[] keyBytes = EncryptionUtils.decodeBASE64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        int i = 0;

        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > 128)
                cache = cipher.doFinal(encryptedData, offSet, 128);
            else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 128;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    private static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = EncryptionUtils.decodeBASE64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        int i = 0;

        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > 117)
                cache = cipher.doFinal(data, offSet, 117);
            else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    private static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = EncryptionUtils.decodeBASE64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        int i = 0;

        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > 117)
                cache = cipher.doFinal(data, offSet, 117);
            else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("RSAPrivateKey");
        return EncryptionUtils.encodeToStringBASE64(key.getEncoded());
    }

    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("RSAPublicKey");
        return EncryptionUtils.encodeToStringBASE64(key.getEncoded());
    }

    /**
     * 加密方法
     *
     * @param value     需要加密参数
     * @param publicKey 公钥
     * @return 返回加密后参数
     */
    public static String sensitiveEncrypt(String value, String publicKey) {
        try {
            return EncryptionUtils.encodeToStringBASE64(encryptByPublicKey(value.getBytes(), publicKey));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("sensitiveEncrypt_e ", e.toString());
        }
        return "";
    }

    /**
     * RSA加密方法
     *
     * @param value 需要加密的值
     * @return 加密后的值
     */
    public static String sensitiveEncryptWithRSA(String value) {
        return sensitiveEncrypt(md5(value), RSA);
    }

    /**
     * 进行md5加密
     *
     * @param str 需要加密md5的字符串
     * @return
     */
    public static String md5(String str) {
        if (str == null) {
            return null;
        }

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            return str;
        } catch (UnsupportedEncodingException e) {
            return str;
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }

    public static String sensitiveDecrypt(String value, String privateKey) {
        try {
            return new String(decryptByPrivateKey(EncryptionUtils.decodeBASE64(value), privateKey));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("sensitiveDecrypt_e ", e.toString());
        }
        return "";
    }

    /**
     * 解密方法
     *
     * @param value     接收加密参数
     * @param publicKey 公钥
     * @return 返回解密后参数
     */
    public static String sensitiveDecryptByPublicKey(String value, String publicKey) {
        try {
            return new String(decryptByPublicKey(EncryptionUtils.decodeBASE64(value), publicKey));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("sensitiveDecrypt_e",e.toString());
        }
        return "";
    }


}
