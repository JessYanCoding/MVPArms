package com.jess.arms.common.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.apkfuns.logutils.LogUtils;

import java.util.List;

/**
 * Call requires API level 4
 * <uses-permission android:name="android.permission.RECEIVE_SMS"/>
 * <p/>
 * action: android.provider.Telephony.SMS_RECEIVED
 *
 * @author lujianzhao
 * @date 14-7-23
 */
public class SmsReceiver extends BroadcastReceiver {
//    private static final String TAG = SmsReceiver.class.getSimpleName();
    private SmsListener smsListener;

    public SmsReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
//            if (LogUtils.getLogConfig().isEnable()) {
//                LogUtils.i("收到广播：" + intent.getAction());
//                Bundle bundle = intent.getExtras();
//                for (String key : bundle.keySet()) {
//                    LogUtils.i( key + " : " + bundle.get(key));
//                }
//            }
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            String fromAddress = null;
            String serviceCenterAddress = null;
            if (pdus != null) {
                String msgBody = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                    for (Object obj : pdus) {
                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                        msgBody += sms.getMessageBody();
                        fromAddress = sms.getOriginatingAddress();
                        serviceCenterAddress = sms.getServiceCenterAddress();

                        if (smsListener != null) {
                            smsListener.onMessage(sms);
                        }
                        //Log.i(TAG, "getDisplayMessageBody：" + sms.getDisplayMessageBody());
                        //Log.i(TAG, "getDisplayOriginatingAddress：" + sms.getDisplayOriginatingAddress());
                        //Log.i(TAG, "getEmailBody：" + sms.getEmailBody());
                        //Log.i(TAG, "getEmailFrom：" + sms.getEmailFrom());
                        //Log.i(TAG, "getMessageBody：" + sms.getMessageBody());
                        //Log.i(TAG, "getOriginatingAddress：" + sms.getOriginatingAddress());
                        //Log.i(TAG, "getPseudoSubject：" + sms.getPseudoSubject());
                        //Log.i(TAG, "getServiceCenterAddress：" + sms.getServiceCenterAddress());
                        //Log.i(TAG, "getIndexOnIcc：" + sms.getIndexOnIcc());
                        //Log.i(TAG, "getMessageClass：" + sms.getMessageClass());
                        //Log.i(TAG, "getUserData：" + new String(sms.getUserData()));
                    }
                }
                if (smsListener != null) {
                    smsListener.onMessage(msgBody, fromAddress, serviceCenterAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerSmsReceiver(Context context, SmsListener smsListener) {
        try {
            this.smsListener = smsListener;
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.setPriority(Integer.MAX_VALUE);
            context.registerReceiver(this, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterSmsReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static abstract class SmsListener {
        public abstract void onMessage(String msg, String fromAddress, String serviceCenterAddress);

        public void onMessage(SmsMessage msg) {}
    }

    /**
     * Call requires API level 4
     * <uses-permission android:name="android.permission.SEND_SMS"/>
     *
     * @param phone
     * @param msg
     */
    public static void sendMsgToPhone(String phone, String msg) {
        LogUtils.i("发送手机：" + phone + " ,内容： " + msg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            SmsManager manager = SmsManager.getDefault();
            List<String> texts = manager.divideMessage(msg);
            for (String txt : texts) {
                manager.sendTextMessage(phone, null, txt, null, null);
            }
        }else{
            LogUtils.e("发送失败，系统版本低于DONUT，" + phone + " ,内容： " + msg);
        }

    }

    /**
     * Call requires API level 4
     * <uses-permission android:name="android.permission.WRITE_SMS"/>
     *
     * @param context
     * @param phone
     * @param msg
     */
    public static void saveMsgToSystem(Context context, String phone, String msg) {
        ContentValues values = new ContentValues();
        values.put("date", System.currentTimeMillis());
        //阅读状态 
        values.put("read", 0);
        //1为收 2为发  
        values.put("type", 2);
        values.put("address", phone);
        values.put("body", msg);
        context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
    }

}
