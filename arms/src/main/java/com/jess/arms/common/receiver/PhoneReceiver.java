package com.jess.arms.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.jess.arms.common.assist.Check;


/**
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
 * <p/>
 * action: android.intent.action.PHONE_STATE;  android.intent.action.NEW_OUTGOING_CALL;
 * <p/>
 * 去电时：
 * 未接：phone_state=OFFHOOK;
 * 挂断：phone_state=IDLE
 * 来电时:
 * 未接：phone_state=RINGING
 * 已接：phone_state=OFFHOOK;
 * 挂断：phone_state=IDLE
 *
 * @author lujianzhao
 * @date 2015-03-09
 */
public class PhoneReceiver extends BroadcastReceiver {

//    private static final String TAG = "PhoneReceiver";

    private static final String RINGING = "RINGING";
    private static final String OFFHOOK = "OFFHOOK";
    private static final String IDLE = "IDLE";

    private static final String PHONE_STATE = "android.intent.action.PHONE_STATE";
    private static final String NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    private static final String INTENT_STATE = "state";
    private static final String INTENT_INCOMING_NUMBER = "incoming_number";
    private PhoneListener phoneListener;
    private boolean isDialOut;
    private String number;

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (LogUtils.getLogConfig().isEnable()) {
//            LogUtils.i( "action: " + intent.getAction());
//            LogUtils.d("intent : ");
//            Bundle bundle = intent.getExtras();
//            for (String key : bundle.keySet()) {
//                LogUtils.d( key + " : " + bundle.get(key));
//            }
//        }
        if (NEW_OUTGOING_CALL.equals(intent.getAction())) {
            isDialOut = true;
            String outNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            if (!Check.isEmpty(outNumber)) {
                this.number = outNumber;
            }
            if (phoneListener != null) {
                phoneListener.onPhoneStateChanged(CallState.Outgoing, number);
            }
        } else if (PHONE_STATE.equals(intent.getAction())) {
            String state = intent.getStringExtra(INTENT_STATE);
            String inNumber = intent.getStringExtra(INTENT_INCOMING_NUMBER);
            if (!Check.isEmpty(inNumber)) {
                this.number = inNumber;
            }
            if (RINGING.equals(state)) {
                isDialOut = false;
                if (phoneListener != null) {
                    phoneListener.onPhoneStateChanged(CallState.IncomingRing, number);
                }
            } else if (OFFHOOK.equals(state)) {
                if (!isDialOut && phoneListener != null) {
                    phoneListener.onPhoneStateChanged(CallState.Incoming, number);
                }
            } else if (IDLE.equals(state)) {
                if (isDialOut) {
                    if (phoneListener != null) {
                        phoneListener.onPhoneStateChanged(CallState.OutgoingEnd, number);
                    }
                } else {
                    if (phoneListener != null) {
                        phoneListener.onPhoneStateChanged(CallState.IncomingEnd, number);
                    }
                }
            }
        }
    }

    /**
     * 去电时：
     * 未接：phone_state=OFFHOOK;
     * 挂断：phone_state=IDLE
     * 来电时:
     * 未接：phone_state=RINGING
     * 已接：phone_state=OFFHOOK;
     * 挂断：phone_state=IDLE
     */
    //public void registerCallStateListener(Context context, PhoneStateListener listener) {
    //    try {
    //        //获取电话通讯服务
    //        TelephonyManager tpm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    //        tpm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}
    public void registerReceiver(Context context, PhoneListener phoneListener) {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.PHONE_STATE");
            filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
            filter.setPriority(Integer.MAX_VALUE);
            context.registerReceiver(this, filter);
            this.phoneListener = phoneListener;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface PhoneListener {
        void onPhoneStateChanged(CallState state, String number);
    }

    /**
     * 分别是：
     * <p/>
     * 播出电话
     * 播出电话结束
     * 接入电话铃响
     * 接入通话中
     * 接入通话完毕
     */
    public enum CallState {
        Outgoing,
        OutgoingEnd,
        IncomingRing,
        Incoming,
        IncomingEnd
    }

}
