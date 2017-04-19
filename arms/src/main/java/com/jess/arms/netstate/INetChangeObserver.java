package com.jess.arms.netstate;

/**
 * @Title INetChangeObserver
 * @Package com.ljz.base.netstate
 * @Description 是检测网络改变的观察者
 */
public interface INetChangeObserver
{
	/**
	 * 网络连接上
	 *
	 * @param type 当前的网络状态:
	 *             UnKnown(-1),没有网络
	 *             Wifi(1),WIFI网络
	 *             Net2G(2),2G网络
	 *             Net3G(3),3G网络
	 *             Net4G(4);4G网络
	 */
	void onNetworkConnect(NetWorkUtil.NetWorkType type);

	/**
	 * 网络断开
	 */
	void onNetworkDisConnect();
}
