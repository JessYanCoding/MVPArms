//package me.qing.eye.pubblico.common;
//
//import android.app.NotificationManager;
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//
//import com.Player.web.response.ResponseCommon;
//import com.Player.web.response.ResponseServer;
//import com.Player.web.websocket.ClientCore;
//import com.getui.demo.AlarmUtils;
//import com.igexin.sdk.PushManager;
//import com.jess.arms.utils.LogUtil;
//import com.quvii.eye.constants.Constants;
//import com.quvii.eye.listener.LoadListener;
//
//import butterknife.internal.Utils;
//import me.qing.eye.pubblico.util.SpUtil;
//
//public class ClientCoreHelper {
//
//    public static boolean IS_INIT_CLIENTCORE = false;
//    public static boolean IS_HAS_OPENPUSH = false;
//
//    private static String initClientCorePrepare(Context context) {
//        String str = UserHelper.getInstance().readUserMess(context, 0);
//        String clientID = null;
//        if (null == str || "" == str) {
//            IS_HAS_OPENPUSH = true;
//            AlarmUtils.openPush(context);
//            clientID = PushManager.getInstance().getClientid(context);
//        } else {
//            clientID = str;
//        }
//        AlarmUtils.GETUI_CID = clientID;
//        return clientID;
//    }
//
//    /**
//     * 注册或者忘记密码时，初始化ClientCore对象
//     *
//     * @param context
//     * @param listener
//     * @param num      暂无使用 传入0即可
//     */
//    public static void initClientCore(Context context, LoadListener listener,
//                                      int num) {
//
//    }
//
//    /**
//     * 登陆时，初始化ClientCore对象
//     *
//     * @param context
//     * @param callBack
//     */
//    public static void initClientCore(final Context context,
//                                      final LoadListener callBack) {
//        ClientCore clientCore = ClientCore.getInstance();
//        /*
//         * if (IS_INIT_CLIENTCORE) { return; }
//		 */
//        callBack.onStart();
//        final String clientID = initClientCorePrepare(context);
//        final String userName = UserHelper.getInstance().readUserMess(context,
//                1);
//        int language = Utility.isZh(context) ? 2 : 1;
//        clientCore.setupHost(context, Constants.server, Constants.port,
//                Utility.getImsi(context), language, Constants.custom_flag,
//                String.valueOf(Utility.GetVersionCode(context)), "");
//        clientCore.setClientCustomFlag("C0000", 1);
//        if (TextUtils.isEmpty(userName) || userName.equals("nologinuser")) {
//            getBestServer(context, clientID, callBack);
//            return;
//        }
//        LogUtil.i("登陆 -readUserMess(1):" + userName + "clientID:" + clientID);
//        // 设置用户推送
//        clientCore.setUserPushWithNoLogin(0, userName, clientID, new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                /*LogUtil.i("登陆 -setUserPushWithNoLogin获取服务器:"
//						+ responseCommon.h.toJsonString());*/ //无网络时responseCommon.h出现空指针；
//                NotificationManager nm = (NotificationManager) context
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//                nm.cancel(1);
//                IS_HAS_OPENPUSH = true;
//                AlarmUtils.openPush(context);
//                if (responseCommon != null && responseCommon.h != null
//                        && responseCommon.h.e == 200) {
//                    getBestServer(context, clientID, callBack);
//                } else {
//                    getBestServer(context, clientID, callBack);
//                }
//            }
//        });
//    }
//
//    private static void getBestServer(final Context context,
//                                      final String clientID, final LoadListener callBack) {
//        ClientCore clientCore = ClientCore.getInstance();
//        clientCore.setSaveDirName(Utils.getConfigPath(context));     // Bug：34242 免登陆添加设备问题
//        clientCore.getCurrentBestServer(context, new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                final ResponseServer responseServer = (ResponseServer) msg.obj;
//				/*LogUtil.i("登陆 -getBestServer 获取服务器:"
//						+ responseServer.b.toJsonString());*/
//                if (responseServer != null && responseServer.h != null
//                        && responseServer.h.e == 200) {
//                    IS_INIT_CLIENTCORE = true;
//                    UserHelper.getInstance()
//                            .writeUserMess(context, clientID, 0);
//                    callBack.onSuccess(null);
//                } else {
//                    IS_INIT_CLIENTCORE = false;
//                    UserHelper.getInstance()
//                            .writeUserMess(context, clientID, 0);
//                    boolean isLocalLogin = SpUtil.getInstance(context).isLocalLogin();
//                    if (!isLocalLogin) {
//                        ClientCore.getInstance().RelaseClient();// 清理掉之前的缓存
//                    }
//                    callBack.onFail(null);
//                }
//                super.handleMessage(msg);
//            }
//        });
//
//    }
//
//    public static void getBestServer(final Context context,
//                                     final LoadListener callBack) {
//        ClientCore clientCore = ClientCore.getInstance();
//        int language = Utility.isZh(context) ? 2 : 1;
//        clientCore.setupHost(context, Constants.server, Constants.port,
//                Utility.getImsi(context), language, Constants.custom_flag,
//                String.valueOf(Utility.GetVersionCode(context)), "");
//        clientCore.setSaveDirName(Utils.getConfigPath(context));             // Bug：34242 免登陆添加设备问题
//        clientCore.getCurrentBestServer(context, new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                final ResponseServer responseServer = (ResponseServer) msg.obj;
//				/*LogUtil.i("登陆 -getBestServer 获取服务器:"
//						+ responseServer.b.toJsonString());*/
//                if (responseServer != null && responseServer.h != null
//                        && responseServer.h.e == 200) {
//                    callBack.onSuccess(null);
//                } else {
//                    ClientCore.getInstance().RelaseClient();
//                    callBack.onFail(null);
//                }
//                super.handleMessage(msg);
//            }
//        });
//
//    }
//}
