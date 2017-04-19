package com.jess.arms.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.ArrayList;


/**
 * @Title NetworkStateReceiver
 * @Package com.ljz.base.netstate
 * @Description 是一个检测网络状态改变的，需要配置 <receiver
 *              android:name="com.ljz.base.netstate.NetworkStateReceiver" >
 *              <intent-filter> <action
 *              android:name="android.net.conn.CONNECTIVITY_CHANGE" /> <action
 *              android:name="android.gzcpc.conn.CONNECTIVITY_CHANGE" />
 *              </intent-filter> </receiver>
 * 
 *              需要开启权限 <uses-permission
 *              android:name="android.permission.CHANGE_NETWORK_STATE" />
 *              <uses-permission
 *              android:name="android.permission.CHANGE_WIFI_STATE" />
 *              <uses-permission
 *              android:name="android.permission.ACCESS_NETWORK_STATE" />
 *              <uses-permission
 *              android:name="android.permission.ACCESS_WIFI_STATE" />
 */
public class NetworkStateReceiver extends BroadcastReceiver {
	private static Boolean networkAvailable = false;
	private static NetWorkUtil.NetWorkType netType;
	private static ArrayList<INetChangeObserver> netChangeObserverArrayList = new ArrayList<INetChangeObserver>();
	private final static String ANDROID_NET_CHANGE_ACTION = ConnectivityManager.CONNECTIVITY_ACTION;//"android.net.conn.CONNECTIVITY_CHANGE";
	private static BroadcastReceiver receiver;

	private static BroadcastReceiver getReceiver() {
		if (receiver == null) {
			receiver = new NetworkStateReceiver();
		}
		return receiver;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		receiver = NetworkStateReceiver.this;
		if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
			// TALogger.i(NetworkStateReceiver.this, "网络状态改变.");
			netType = NetWorkUtil.getNetworkType(context);

			if (!NetWorkUtil.isAvailable(context) || netType == NetWorkUtil.NetWorkType.UnKnown) {
				// TALogger.i(NetworkStateReceiver.this, "没有网络连接.");
				networkAvailable = false;
			} else {
				// TALogger.i(NetworkStateReceiver.this, "网络连接成功.");
				networkAvailable = true;
			}
			notifyObserver();
		}
	}

	/**
	 * 注册网络状态广播
	 */
	public static void registerNetworkStateReceiver(Context mContext) {
		IntentFilter filter = new IntentFilter();
		//filter.addAction(TA_ANDROID_NET_CHANGE_ACTION);
		filter.addAction(ANDROID_NET_CHANGE_ACTION);
		mContext.getApplicationContext()
				.registerReceiver(getReceiver(), filter);
	}

	/**
	 * 检查网络状态
	 */
	public static void checkNetworkState(Context mContext) {
		Intent intent = new Intent();
		//intent.setAction(TA_ANDROID_NET_CHANGE_ACTION);
		mContext.sendBroadcast(intent);
	}

	/**
	 * 注销网络状态广播
	 */
	public static void unRegisterNetworkStateReceiver(Context mContext) {
		if (receiver != null) {
			try {
				mContext.getApplicationContext().unregisterReceiver(receiver);
			} catch (Exception e) {
				// TODO: handle exception
				//TALogger.d("TANetworkStateReceiver", e.getMessage());
			}
		}

	}

	/**
	 * 获取当前网络状态，true为网络连接成功，否则网络连接失败
	 */
	public static Boolean isNetworkAvailable() {
		return networkAvailable;
	}

	public static NetWorkUtil.NetWorkType getAPNType() {
		return netType;
	}

	private void notifyObserver() {

		for (int i = 0; i < netChangeObserverArrayList.size(); i++) {
			INetChangeObserver observer = netChangeObserverArrayList.get(i);
			if (observer != null) {
				if (isNetworkAvailable()) {
					observer.onNetworkConnect(netType);
				} else {
					observer.onNetworkDisConnect();
				}
			}
		}

	}

	/**
	 * 注册网络连接观察者
	 */
	public static void registerObserver(INetChangeObserver observer) {
		if (netChangeObserverArrayList == null) {
			netChangeObserverArrayList = new ArrayList<INetChangeObserver>();
		}
		netChangeObserverArrayList.add(observer);
	}

	/**
	 * 注销网络连接观察者
	 */
	public static void removeRegisterObserver(INetChangeObserver observer) {
		if (netChangeObserverArrayList != null) {
			netChangeObserverArrayList.remove(observer);
		}
	}

}