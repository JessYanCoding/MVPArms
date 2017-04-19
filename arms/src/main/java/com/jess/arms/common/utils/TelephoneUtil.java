package com.jess.arms.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.jess.arms.common.assist.Check;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Get phone info, such as IMEI,IMSI,Number,Sim State, etc.
 * <p>
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 *
 * @author lujianzhao
 * @date 2014-09-25
 */
public class TelephoneUtil {

    /**
     * 获取设备唯一ID
     * 先以IMEI为准，如果IMEI为空，则androidId
     * 下次使用deviceId的时候优先从外部存储读取，再从背部存储读取，最后在重新生成，尽可能的保证其不变性
     *
     * @param activity
     * @return
     */
    public static Observable<String> getDeviceId(final Activity activity) {
       return new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .flatMap(new Function<Boolean, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Boolean granted) throws Exception {
                        String path = null;
                        if (granted) {
                            path = getExternalStoragePath();
                        }

                        if (Check.isEmpty(path)) {
                            path = getPath();
                        }
                        String deviceId = readAndWriteDeviceId(activity, path);
                        return Observable.just(deviceId);
                    }
                });
    }

    private static String readAndWriteDeviceId(Context context,String path) {
        String deviceId = FileUtil.getFileOutputString(path);
        if (isEmpty(deviceId)) {
            //是否是MTK机型
            TeleInfo mtkTeleInfo = getMtkTeleInfo(context);
            if (mtkTeleInfo != null && !isEmpty(mtkTeleInfo.imei_1) && !isEmpty(mtkTeleInfo.imei_2)) {
                deviceId = mtkTeleInfo.imei_1 + mtkTeleInfo.imei_2;
            }

            //是否是MTK2机型
            if (isEmpty(deviceId)) {
                TeleInfo mtkTeleInfo2 = getMtkTeleInfo2(context);
                if (mtkTeleInfo2 != null && !isEmpty(mtkTeleInfo2.imei_1) && !isEmpty(mtkTeleInfo2.imei_2)) {
                    deviceId = mtkTeleInfo2.imei_1 + mtkTeleInfo2.imei_2;
                }
            }

            //是否是高通机型
            if (isEmpty(deviceId)) {
                TeleInfo qualcommTeleInfo = getQualcommTeleInfo(context);
                if (qualcommTeleInfo != null && !isEmpty(qualcommTeleInfo.imei_1) && !isEmpty(qualcommTeleInfo.imei_2)) {
                    deviceId = qualcommTeleInfo.imei_1 + qualcommTeleInfo.imei_2;
                }
            }

            //是否是展讯机型
            if (isEmpty(deviceId)) {
                TeleInfo spreadtrumTeleInfo = getSpreadtrumTeleInfo(context);
                if (spreadtrumTeleInfo != null  && !isEmpty(spreadtrumTeleInfo.imei_1) && !isEmpty(spreadtrumTeleInfo.imei_2)) {
                    deviceId = spreadtrumTeleInfo.imei_1 + spreadtrumTeleInfo.imei_2;
                }
            }

            if (isEmpty(deviceId)) {
                deviceId = getIMEI(context);
            }

            if (isEmpty(deviceId)) {
                deviceId = getUniversalID(context);
            }

            if (!isEmpty(deviceId)) {
                saveDeviceId(deviceId,path);
            }
        }
        return deviceId;
    }

    private static boolean isEmpty(String deviceId) {
        return Check.isEmpty(deviceId) || Pattern.matches("^0+$", deviceId);
    }

    /**
     * 首先通过读取Android_id,作为UUID的种子。若得到Android_Id等于9774d56d682e549c 或者 发生错误
     * 则 random 一个 UUID 作为备用方案
     */
    public static String getUniversalID(Context context) {
        String uuid;
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!Check.isEmpty(androidId) && !"9774d56d682e549c".equals(androidId)) {
            try {
                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString().replaceAll("-", "");
            } catch (Exception e) {
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
        } else {
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
        }
        return uuid;
    }

    private static void saveDeviceId(String deviceId,String path) {
        FileUtil.writeFile(deviceId, path, false);
    }

    private static String getExternalStoragePath() {
        String dir = FileUtil.getExternalStorageDirectory("UTips");
        if (Check.isEmpty(dir)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(dir);
        sb.append(File.separator);
        sb.append("device_id");
        return sb.toString();
    }


    private static String getPath() {
        StringBuilder sb = new StringBuilder(FileUtil.getDocumentDir());
        sb.append(File.separator);
        sb.append("device_id");
        return sb.toString();
    }


    /**
     * IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)
     * IMSI共有15位，其结构如下：
     * MCC+MNC+MIN
     * MCC：Mobile Country Code，移动国家码，共3位，中国为460;
     * MNC:Mobile NetworkCode，移动网络码，共2位
     * 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
     * 合起来就是（也是Android手机中APN配置文件中的代码）：
     * 中国移动：46000 46002
     * 中国联通：46001
     * 中国电信：46003
     * 举例，一个典型的IMSI号码为460030912121001
     */
    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = telephonyManager.getSubscriberId();
        LogUtils.i(" IMSI：" + IMSI);
        return IMSI;
    }

    /**
     * IMEI是International Mobile Equipment Identity （国际移动设备标识）的简称
     * IMEI由15位数字组成的”电子串号”，它与每台手机一一对应，而且该码是全世界唯一的
     * 其组成为：
     * 1. 前6位数(TAC)是”型号核准号码”，一般代表机型
     * 2. 接着的2位数(FAC)是”最后装配号”，一般代表产地
     * 3. 之后的6位数(SNR)是”串号”，一般代表生产顺序号
     * 4. 最后1位数(SP)通常是”0″，为检验码，目前暂备用
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();
        LogUtils.i(" IMEI：" + IMEI);
        return IMEI;
    }

    /**
     * 获取当前设置的电话号码
     */
    public static String getNativePhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String NativePhoneNumber = null;
        NativePhoneNumber = telephonyManager.getLine1Number();
        return String.format("手机号: %s", NativePhoneNumber);
    }

    /**
     * Print telephone info.
     */
    public static String printTelephoneInfo(Context context) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______ 手机信息  ").append(time).append(" ______________");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = tm.getSubscriberId();
        //IMSI前面三位460是国家号码，其次的两位是运营商代号，00、02是中国移动，01是联通，03是电信。
        String providerName = null;
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                providerName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                providerName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                providerName = "中国电信";
            }
        }
        sb.append(providerName).append("  手机号：").append(tm.getLine1Number()).append(" IMSI是：").append(IMSI);
        sb.append("\nDeviceID(IMEI)       :").append(tm.getDeviceId());
        sb.append("\nDeviceSoftwareVersion:").append(tm.getDeviceSoftwareVersion());
        sb.append("\ngetLine1Number       :").append(tm.getLine1Number());
        sb.append("\nNetworkCountryIso    :").append(tm.getNetworkCountryIso());
        sb.append("\nNetworkOperator      :").append(tm.getNetworkOperator());
        sb.append("\nNetworkOperatorName  :").append(tm.getNetworkOperatorName());
        sb.append("\nNetworkType          :").append(tm.getNetworkType());
        sb.append("\nPhoneType            :").append(tm.getPhoneType());
        sb.append("\nSimCountryIso        :").append(tm.getSimCountryIso());
        sb.append("\nSimOperator          :").append(tm.getSimOperator());
        sb.append("\nSimOperatorName      :").append(tm.getSimOperatorName());
        sb.append("\nSimSerialNumber      :").append(tm.getSimSerialNumber());
        sb.append("\ngetSimState          :").append(tm.getSimState());
        sb.append("\nSubscriberId         :").append(tm.getSubscriberId());
        sb.append("\nVoiceMailNumber      :").append(tm.getVoiceMailNumber());

        LogUtils.i(sb.toString());
        return sb.toString();
    }

    /////_________________ 双卡双待系统IMEI和IMSI方案（see more on http://benson37.iteye.com/blog/1923946）

    /**
     * 双卡双待神机IMSI、IMSI、PhoneType信息
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static class TeleInfo {
        public String imsi_1;
        public String imsi_2;
        public String imei_1;
        public String imei_2;
        public int phoneType_1;
        public int phoneType_2;

        @Override
        public String toString() {
            return "TeleInfo{" +
                    "imsi_1='" + imsi_1 + '\'' +
                    ", imsi_2='" + imsi_2 + '\'' +
                    ", imei_1='" + imei_1 + '\'' +
                    ", imei_2='" + imei_2 + '\'' +
                    ", phoneType_1=" + phoneType_1 +
                    ", phoneType_2=" + phoneType_2 +
                    '}';
        }
    }

    /**
     * MTK Phone.
     * <p>
     * 获取 MTK 神机的双卡 IMSI、IMSI 信息
     */
    public static TeleInfo getMtkTeleInfo(Context context) {
        try {
            TeleInfo teleInfo = new TeleInfo();

            Class<?> phone = Class.forName("com.android.internal.telephony.Phone");

            Field fields1 = phone.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            int simId_1 = (Integer) fields1.get(null);

            Field fields2 = phone.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            int simId_2 = (Integer) fields2.get(null);

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method getSubscriberIdGemini = TelephonyManager.class.getDeclaredMethod("getSubscriberIdGemini", int.class);
            String imsi_1 = (String) getSubscriberIdGemini.invoke(tm, simId_1);
            String imsi_2 = (String) getSubscriberIdGemini.invoke(tm, simId_2);
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imsi_2 = imsi_2;

            Method getDeviceIdGemini = TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", int.class);
            String imei_1 = (String) getDeviceIdGemini.invoke(tm, simId_1);
            String imei_2 = (String) getDeviceIdGemini.invoke(tm, simId_2);

            teleInfo.imei_1 = imei_1;
            teleInfo.imei_2 = imei_2;

            Method getPhoneTypeGemini = TelephonyManager.class.getDeclaredMethod("getPhoneTypeGemini", int.class);
            int phoneType_1 = (Integer) getPhoneTypeGemini.invoke(tm, simId_1);
            int phoneType_2 = (Integer) getPhoneTypeGemini.invoke(tm, simId_2);
            teleInfo.phoneType_1 = phoneType_1;
            teleInfo.phoneType_2 = phoneType_2;
            LogUtils.i("MTK: " + teleInfo);
            return teleInfo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MTK Phone.
     * <p>
     * 获取 MTK 神机的双卡 IMSI、IMSI 信息
     */
    public static TeleInfo getMtkTeleInfo2(Context context) {
        try {
            TeleInfo teleInfo = new TeleInfo();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> phone = Class.forName("com.android.internal.telephony.Phone");
            Field fields1 = phone.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            int simId_1 = (Integer) fields1.get(null);
            Field fields2 = phone.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            int simId_2 = (Integer) fields2.get(null);

            Method getDefault = TelephonyManager.class.getMethod("getDefault", int.class);
            TelephonyManager tm1 = (TelephonyManager) getDefault.invoke(tm, simId_1);
            TelephonyManager tm2 = (TelephonyManager) getDefault.invoke(tm, simId_2);

            String imsi_1 = tm1.getSubscriberId();
            String imsi_2 = tm2.getSubscriberId();
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imsi_2 = imsi_2;

            String imei_1 = tm1.getDeviceId();
            String imei_2 = tm2.getDeviceId();
            teleInfo.imei_1 = imei_1;
            teleInfo.imei_2 = imei_2;

            int phoneType_1 = tm1.getPhoneType();
            int phoneType_2 = tm2.getPhoneType();
            teleInfo.phoneType_1 = phoneType_1;
            teleInfo.phoneType_2 = phoneType_2;

            LogUtils.i("MTK2: " + teleInfo);
            return teleInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Qualcomm Phone.
     * 获取 高通 神机的双卡 IMSI、IMSI 信息
     */
    public static TeleInfo getQualcommTeleInfo(Context context) {

        try {
            TeleInfo teleInfo = new TeleInfo();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> simTMclass = Class.forName("android.telephony.MSimTelephonyManager");
            Object sim = context.getSystemService(Context.TELEPHONY_SERVICE);
            int simId_1 = 0;
            int simId_2 = 1;

            Method getSubscriberId = simTMclass.getMethod("getSubscriberId", int.class);
            String imsi_1 = (String) getSubscriberId.invoke(sim, simId_1);
            String imsi_2 = (String) getSubscriberId.invoke(sim, simId_2);
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imsi_2 = imsi_2;

            Method getDeviceId = simTMclass.getMethod("getDeviceId", int.class);
            String imei_1 = (String) getDeviceId.invoke(sim, simId_1);
            String imei_2 = (String) getDeviceId.invoke(sim, simId_2);
            teleInfo.imei_1 = imei_1;
            teleInfo.imei_2 = imei_2;

            Method getDataState = simTMclass.getMethod("getDataState");
            int phoneType_1 = tm.getDataState();
            int phoneType_2 = (Integer) getDataState.invoke(sim);
            teleInfo.phoneType_1 = phoneType_1;
            teleInfo.phoneType_2 = phoneType_2;

            Log.i(TAG, "Qualcomm: " + teleInfo);
            return teleInfo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Spreadtrum Phone.
     * <p>
     * 获取 展讯 神机的双卡 IMSI、IMSI 信息
     */
    public static TeleInfo getSpreadtrumTeleInfo(Context context) {

        try {
            TeleInfo teleInfo = new TeleInfo();

            TelephonyManager tm1 = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imsi_1 = tm1.getSubscriberId();
            String imei_1 = tm1.getDeviceId();
            int phoneType_1 = tm1.getPhoneType();
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imei_1 = imei_1;
            teleInfo.phoneType_1 = phoneType_1;

            Class<?> phoneFactory = Class.forName("com.android.internal.telephony.PhoneFactory");
            Method getServiceName = phoneFactory.getMethod("getServiceName", String.class, int.class);
            getServiceName.setAccessible(true);
            TelephonyManager tm2 = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imsi_2 = tm2.getSubscriberId();
            String imei_2 = tm2.getDeviceId();
            int phoneType_2 = tm2.getPhoneType();
            teleInfo.imsi_2 = imsi_2;
            teleInfo.imei_2 = imei_2;
            teleInfo.phoneType_2 = phoneType_2;
            Log.i(TAG, "Spreadtrum: " + teleInfo);
            return teleInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}