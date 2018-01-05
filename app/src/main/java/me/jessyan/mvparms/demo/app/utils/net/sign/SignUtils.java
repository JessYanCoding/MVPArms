package me.jessyan.mvparms.demo.app.utils.net.sign;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 掌沃生成验签工具
 * Created by tao on 2016-07-14.
 */
public class SignUtils {

    // 掌沃生活 app 验签key
    private static String securityKey = "MWsrfy9h8D2FrPxZL9adOaxETqCrRYky";

    private static boolean rsaValidateSign(Map<String, String> parma,
                                           String publicKey) {
        try {
            String sign = (String) parma.get("sign");
            sign = new String(EncryptionUtils.decodeBASE64(sign));
            parma.remove("sign");
            String md5Str = buildSignature(parma);
            return RSAUtils.verify(md5Str.getBytes(), publicKey, sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private static Map<String, String> paraFilter(Map<String, String> para) {
        Map result = new HashMap();

        if ((para == null) || (para.size() <= 0)) {
            return result;
        }

        for (String key : para.keySet()) {
            String value = (String) para.get(key);
            if (!key.equalsIgnoreCase("sign")) {
                result.put(key, value);
            }
        }
        return result;
    }

    private static String createValueString(Map<String, String> para) {
        para = paraFilter(para);

        List keys = new ArrayList(para.keySet());

        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) para.get(key);

            if (value != null) {
                sb.append(key);
                sb.append("=");
                sb.append(value);
                sb.append("&");
            }
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

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

    /**
     * 生成sign
     *
     * @param req 封装后的参数map
     * @return sign
     */
    public static String buildSignature(Map<String, String> req) {
        String prestr = createValueString(req);
        prestr = prestr + "&" + md5(securityKey);
        return md5(prestr);
    }

    public static Signature[] getRawSignature(Context context, String pkgName) {
        if ((pkgName == null) || (pkgName.length() == 0)) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
            if (pi == null) {
                return null;
            }
            return pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }


}
