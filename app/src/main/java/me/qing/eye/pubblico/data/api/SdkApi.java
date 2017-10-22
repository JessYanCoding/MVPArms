package me.qing.eye.pubblico.data.api;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.Player.web.response.ResponseCommon;
import com.Player.web.websocket.ClientCore;

import me.qing.eye.pubblico.app.MyApp;
import me.qing.eye.pubblico.constants.AppConst;
import me.qing.eye.pubblico.util.LogUtil;

public class SdkApi {
    public static final String SdkApi_Error = "SdkApi_Error";

    public static Context mContext = MyApp.getInstance();


    /**
     * ClientCore
     */

    /**
     * 登录
     *
     */
    public static void loginServerAtUserId(ClientCore clientCore, String userName,
                                           String pwd, Handler handler) {
        clientCore.loginServerAtUserId(mContext, userName, pwd, new Handler() {

            @Override
            public void handleMessage(Message msg) {
                ResponseCommon responseCommon = (ResponseCommon) msg.obj;
                if (responseCommon != null && responseCommon.h != null) {
                    if (responseCommon.h.e == 200) {
                        // TODO: 2017/10/22 常量是否要放到Model中
                        // TODO: 2017/10/22 直接返回e会不会更好
                        // TODO: 2017/10/22 但是这个是回调啊！！！！
                        handler.sendEmptyMessage(AppConst.LOGIN_OK);
                    } else if (responseCommon.h.e == 406) {
                        handler.sendEmptyMessage(AppConst.LOGIN_USER_OR_PWD_ERROR);
                    } else if (responseCommon.h.e == 500) {
                        handler.sendEmptyMessage(AppConst.LUNCH_FAILED);
                    } else {
                        LogUtil.i("登录失败!code=" + responseCommon.h.e);
                        handler.sendEmptyMessage(AppConst.LOGIN_FAILED);
                    }
                } else {
                    LogUtil.i("登录失败! error=" + msg.what);
                    handler.sendEmptyMessage(AppConst.LOGIN_FAILED);
                }
//                super.handleMessage(msg);
            }
        });
    }
//    /*
//
//    /**
//     * 注册账户
//     *
//     * @param pc
//     * @param aUserId      用户ID 必填
//     * @param aPassword    密码 必填
//     * @param user_email   邮箱 必填
//     * @param nickName     昵称
//     * @param user_phone   手机号码
//     * @param user_id_card 用户身份证id
//     */
//    /**
//     * 注册账户
//     *
//     * @param context
//     * @param clientCore
//     * @param aUserId      用户ID 必填
//     * @param aPassword    密码 必填
//     * @param user_email   邮箱 必填
//     * @param nickName     昵称
//     * @param user_phone   手机号码
//     * @param user_id_card 用户身份证id
//     * @param handler
//     */
//    public static void registeredUser(final Context context,
//                                      final ClientCore clientCore, String aUserId, String aPassword,
//                                      String user_email, String nickName, String user_phone,
//                                      String user_id_card, final Handler handler) {
//        clientCore.registeredUser(aUserId, aPassword, user_email, nickName,
//                user_phone, user_id_card, new Handler() {
//
//                    @Override
//                    public void handleMessage(Message msg) {
//                        ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                        if (responseCommon != null && responseCommon.h != null) {
//                            if (responseCommon.h.e == 200) {
//                                handler.sendEmptyMessage(AppConst.REGIST_S);
//                            } else if (responseCommon.h.e == 409) {// 用户已存在
//                                handler.sendEmptyMessage(AppConst.REGIST_F_USER_EXIST);
//                            } else {
//                                LogUtil.i("注册失败!code=" + responseCommon.h.e);
//                                handler.sendEmptyMessage(AppConst.REGIST_F);
//                            }
//                        } else {
//                            LogUtil.i("注册失败! error=" + msg.what);
//                            handler.sendEmptyMessage(AppConst.REGIST_F);
//                        }
//                        super.handleMessage(msg);
//                    }
//                });
//    }
//    /*
//
//    /**
//     * 注销
//     *
//     * @param activity
//     * @param clientCore
//     * @param disableAlarm 是否取消报警推送
//     * @param handler
//     */
//    public static void logoutServer(final Activity activity,
//                                    final ClientCore clientCore, int disableAlarm, final Handler handler) {
//        clientCore.logoutServer(disableAlarm, new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                if (responseCommon != null && responseCommon.h != null) {
//                    if (responseCommon.h.e == 200) {
//                        handler.sendEmptyMessage(AppConst.LOGOUT_S);
//                    } else {
//                        LogUtil.i("注销失败!code=" + responseCommon.h.e);
//                        handler.sendEmptyMessage(AppConst.LOGOUT_F);
//                    }
//
//                } else {
//                    LogUtil.i("注销失败! error=" + msg.what);
//                    handler.sendEmptyMessage(AppConst.LOGOUT_F);
//                }
//                super.handleMessage(msg);
//            }
//
//        });
//
//    }
//
//    /**
//     * 修改密码  暂时未用
//     *
//     * @param context
//     * @param clientCore
//     * @param oldPassword 旧密码
//     * @param newPassword 新密码
//     */
//    public static void modifyUserPassword(final Context context,
//                                          final ClientCore clientCore, String oldPassword, String newPassword) {
//        clientCore.modifyUserPassword(oldPassword, newPassword, new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                // TODO Auto-generated method stub
//                ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                if (responseCommon != null && responseCommon.h != null) {
//                    if (responseCommon.h.e == 200) {
//                        LogUtil.i("修改密码成功");
//                    } else {
//                        LogUtil.i("修改密码失败!code=" + responseCommon.h.e);
//                    }
//                } else {
//                    LogUtil.i("修改密码失败! error=" + msg.what);
//                }
//                super.handleMessage(msg);
//            }
//
//        });
//
//    }
//
//    /**
//     * 发送重置密码邮件
//     *
//     * @param context
//     * @param clientCore
//     * @param user_id    需要重置密码的用户名
//     * @param language   语言：1表示英文(English)，2表示简体中文(SimpChinese)， 3表示繁体中文(TradChinese)
//     * @param handler
//     */
//    public static void resetUserPassword(final Context context,
//                                         final ClientCore clientCore, String user_id, int language,
//                                         final Handler handler) {
//        clientCore.resetUserPassword(user_id, language, new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                // TODO Auto-generated method stub
//                ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                if (responseCommon != null && responseCommon.h != null) {
//                    if (responseCommon.h.e == 200) {
//                        handler.sendEmptyMessage(AppConst.RESET_PASSWORD_S);
//                    } else {
//                        LogUtil.i("发送重置密码失败!code=" + responseCommon.h.e);
//                        handler.sendEmptyMessage(AppConst.RESET_PASSWORD_F);
//                    }
//                } else {
//                    LogUtil.i("发送重置密码失败! error=" + msg.what);
//                    handler.sendEmptyMessage(AppConst.RESET_PASSWORD_F);
//                }
//                super.handleMessage(msg);
//            }
//
//        });
//
//    }
//
//    /**
//     * @param context
//     * @param clientCore
//     * @param parent_node_id 父节点ID
//     * @param page_index     分页功能，指定从第几页开始，是可选的，默认不分页；
//     * @param page_size      分页功能，每页返回的记录数，是可选的，默认不分页；
//     * @param handler
//     */
//    public static void getNodeList(final Context context,
//                                   final ClientCore clientCore, int parent_node_id, int page_index,
//                                   int page_size, final Handler handler) {
//        final ArrayList<Device> nodeList = new ArrayList<Device>();
//        clientCore.setSaveDirName(Utils.getConfigPath(context)); //设置缓存路径
//        clientCore.getNodeList(parent_node_id, page_index, page_size,
//                new Handler() {
//
//                    @Override
//                    public void handleMessage(Message msg) {
//
//                        ResponseDevList responseDevList = (ResponseDevList) msg.obj;
//                        if (responseDevList != null
//                                && responseDevList.h != null && responseDevList.b != null) {
//                            List<DevItemInfo> items = responseDevList.b.nodes;
//                            for (int i = 0; i < items.size(); i++) {
//                                DevItemInfo devItemInfo = items.get(i);
//                                if (devItemInfo != null) {
//                                    PlayNode node = PlayNode
//                                            .ChangeData(devItemInfo);
//                                    Device device = new Device();
//                                    device.setPlaynode(node);
//                                    nodeList.add(device);
//                                    AndroidApplication.deviceMap.put(
//                                            device.getDeviceId(), device);
//                                    AndroidApplication.namedeviceMap.put(
//                                            device.getDevicename(), device);
//                                }
//                            }
//                            if (responseDevList.h.e == 200) {
//                                handler.sendMessage(Message.obtain(handler,
//                                        AppConst.GET_DEVLIST_S, nodeList));
//                                LogUtil.i("getNodeList" + nodeList.size());
//                            } else {
//                                LogUtil.i("获取设备列表失败!code=" + responseDevList.h.e);
//                                handler.sendEmptyMessage(AppConst.GET_DEVLIST_F);
//                            }
//                        } else {
//                            LogUtil.i("获取设备列表失败! error=" + msg.what);
//                            handler.sendEmptyMessage(AppConst.GET_DEVLIST_F);
//                        }
//                        super.handleMessage(msg);
//                    }
//                });
//    }
//
//    /**
//     * 添加设备节点
//     *
//     * @param context
//     * @param clientCore
//     * @param node_name      名称
//     * @param parent_node_id 父节点ID
//     * @param node_type      节点类型 : 0表示目录节点、1表示设备节点、2表示摄像机节点；
//     * @param conn_mode      当node_type不为0的时候是必须的 连接模式: 0表示直连模式、1表示流媒体服务器模式、
//     *                       2表示P2P云模式、3表示云流媒体模式；
//     * @param vendor_id      厂商ID，当node_type不为0的时候是必须的，取值范围：
//     *                       1001表示华科流媒体协议（bit）、1002表示华科流媒体协议（XML）、
//     *                       1003表示华科流媒体协议（JSON）、1004表示华科流媒体协议（OWSP）、
//     *                       1005表示原华科流媒体服务器协议（HKSP）、1006表示文件摄像机协议（FCAM）、
//     *                       1007表示华科监控通讯协议（HMCP）、1008表示华科设备上连协议（HDTP）、
//     *                       1009表示UMSP、1010表示EPMY、1011表示RTSP、
//     *                       1012表示HTTP、1013表示SIP、1014表示RTMP、
//     *                       2010表示杭州海康、2011表示杭州海康推模式、2020表示杭州大华、
//     *                       2030表示深圳锐明、2040表示深圳黄河、2050表示深圳汉邦、
//     *                       2060表示杭州雄迈、2070表示中山立堡、2080表示成都索贝、
//     *                       2090表示上海皓维、2100表示金三立、2110表示上海通立、
//     *                       2120表示深圳郎驰、2130表示网视通、2140表示广州奇幻、
//     *                       2150表示安联锐视、2160表示广州佳可、2170表示深圳旗翰、 2180表示瀚晖威视。
//     * @param dev_umid       设备umid
//     * @param dev_addr       设备IP或者域名
//     * @param dev_port       设备端口
//     * @param dev_user       设备用户名
//     * @param dev_passwd     设备密码
//     * @param dev_ch_num     设备通道数 dvr/nvr通道数
//     * @param dev_ch_no      设备通道号 node_type为2时有效，添加dvr/nvr指定的通道号
//     * @param dev_stream_no  设备请求码流 0:主码流，1，子码流
//     * @param handler
//     */
//    public static void addNodeInfo(final Context context,
//                                   final ClientCore clientCore, String node_name, int parent_node_id,
//                                   int node_type, int conn_mode, int vendor_id, String dev_umid,
//                                   String dev_addr, int dev_port, String dev_user, String dev_passwd,
//                                   int dev_ch_num, int dev_ch_no, int dev_stream_no,
//                                   final Handler handler) {
//        clientCore.addNodeInfo(node_name, parent_node_id, node_type, conn_mode,
//                vendor_id, dev_umid, dev_addr, dev_port, dev_user, dev_passwd,
//                dev_ch_num, dev_ch_no, dev_stream_no, new Handler() {
//
//                    @Override
//                    public void handleMessage(Message msg) {
//                        ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                        if (responseCommon != null && responseCommon.h != null) {
//                            if (responseCommon.h.e == 200) {
//                                LogUtil.i("添加设备成功!");
//                                handler.sendEmptyMessage(AppConst.ADD_DEV_S);
//                            } else {
//                                LogUtil.i("添加设备失败!code=" + responseCommon.h.e);
//                                handler.sendEmptyMessage(AppConst.ADD_DEV_F);
//                            }
//                        } else {
//                            LogUtil.i("添加设备失败! error=" + msg.what);
//                            handler.sendEmptyMessage(AppConst.ADD_DEV_F);
//                        }
//                        super.handleMessage(msg);
//                    }
//
//                });
//
//    }
//
//    /**
//     * 删除设备
//     *
//     * @param context
//     * @param clientCore
//     * @param node_id    节点ID
//     * @param node_type
//     * @param id_type
//     * @param handler
//     */
//    public static void deleteNodeInfo(final Context context,
//                                      final ClientCore clientCore, String node_id, int node_type,
//                                      int id_type, final Handler handler) {
//        clientCore.deleteNodeInfo(node_id, node_type, id_type, new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                if (responseCommon != null && responseCommon.h != null) {
//                    if (responseCommon.h.e == 200) {
//                        handler.sendEmptyMessage(AppConst.DELETE_DEV_S);
//                    } else {
//                        LogUtil.i(" 删除设备设备失败!code=" + responseCommon.h.e);
//                        handler.sendEmptyMessage(AppConst.DELETE_DEV_F);
//                    }
//                } else {
//                    LogUtil.i(" 删除设备设备失败! error=" + msg.what);
//                    handler.sendEmptyMessage(AppConst.DELETE_DEV_F);
//                }
//                super.handleMessage(msg);
//            }
//        });
//
//    }
//
//    /**
//     * 修改设备
//     *
//     * @param context
//     * @param clientCore
//     * @param node_id       节点ID
//     * @param node_name     名称
//     * @param dev_umid      设备umid
//     * @param dev_addr      设备IP地址/域名
//     * @param dev_port      设备端口
//     * @param dev_user      设备用户名
//     * @param dev_passwd    设备密码
//     * @param dev_ch_no     摄像机节点才有效， nvr/dvr的通道号
//     * @param dev_stream_no 码流
//     * @param handler
//     */
//    public static void modifyNodeInfo(final Context context,
//                                      final ClientCore clientCore, String node_id, String node_name,
//                                      int node_type, int vendor_id, String dev_umid, String dev_addr,
//                                      int dev_port, String dev_user, String dev_passwd, int dev_ch_no,
//                                      int dev_stream_no, final Handler handler) {
//        clientCore.modifyNodeInfo(node_id, node_name, node_type, 0, vendor_id,
//                dev_umid, dev_addr, dev_port, dev_user, dev_passwd, dev_ch_no,
//                dev_stream_no, new Handler() {
//
//                    @Override
//                    public void handleMessage(Message msg) {
//                        ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                        if (responseCommon != null && responseCommon.h != null) {
//                            if (responseCommon.h.e == 200) {
//                                LogUtil.i("修改设备成功");
//                                handler.sendEmptyMessage(AppConst.MODIFY_DEV_S);
//                            } else {
//                                LogUtil.i("修改设备设备失败!code=" + responseCommon.h.e);
//                                handler.sendEmptyMessage(AppConst.MODIFY_DEV_F);
//                            }
//                        } else {
//                            LogUtil.i("修改设备设备失败! error=" + msg.what);
//                            handler.sendEmptyMessage(AppConst.MODIFY_DEV_F);
//                        }
//                        super.handleMessage(msg);
//                    }
//
//                });
//
//    }
//
//
//    /**
//     * 布防，撤防设置
//     *
//     * @param opCode 为1时布防 为2时撤防 为4撤销所有设备布防
//     */
//    public static void setAlarmPush(final Context context, String deviceId, int[] alarm_event,
//                                    ClientCore clientCore, PlayNode node, final int opCode, final Handler handler) {
//        // 报警类型
//        //int[] alarm_event = { 1, 2, 3, 4, 5 };
//        // 免登陆模式、umid直连模式下 ，需设置设备通道参数 ， 支持多个通道设置，可选
//        //	P2pConnectInfo p2pConnectInfo = createConnectInfo(clientCore, node);
//        //	P2pConnectInfo[] p2pConnectInfos = { p2pConnectInfo };
//        //	clientCore.alarmSettings(p2pConnectInfos);
//        // AlarmUtils.GETUI_CID为推送token
//        clientCore.alarmSettings(opCode, AlarmUtils.GETUI_CID, alarm_event,
//                new Handler() {
//
//                    @Override
//                    public void handleMessage(Message msg) {
//                        ResponseCommon commonSocketText = (ResponseCommon) msg.obj;
//                        if (commonSocketText != null
//                                && commonSocketText.h.e == 200) {
//                            handler.sendEmptyMessage(AppConst.LOAD_SUCCESS);
//                            /*if (opCode == 1) {
//                                Show.toast(context, "布防成功");
//							} else if (opCode == 2) {
//								Show.toast(context, "撤防成功");
//							}*/
//
//                        } else {
//                            handler.sendEmptyMessage(AppConst.LOAD_FAIL);
//                            /*if (opCode == 1) {
//                                Show.toast(context, "布防失败");
//							} else if (opCode == 2) {
//								Show.toast(context, "撤防失败");
//							}*/
//
//                        }
//                    }
//                }, deviceId);
//    }
//
//    /**
//     * 免登录报警 设备通道参数
//     *
//     * @param clientCore
//     * @param node
//     * @return
//     */
//    public static P2pConnectInfo createConnectInfo(ClientCore clientCore,
//                                                   PlayNode node) {
//        P2pConnectInfo p2pConnectInfo = new P2pConnectInfo();
//        if (node != null) {
//            // 解析连接参数
//            TDevNodeInfor info = TDevNodeInfor.changeToTDevNodeInfor(
//                    node.getDeviceId(), node.node.iConnMode);
//            if (info != null) {
//                p2pConnectInfo = new P2pConnectInfo();
//                p2pConnectInfo.umid = info.pDevId;
//                p2pConnectInfo.user = info.pDevUser;
//                p2pConnectInfo.passwd = info.pDevPwd;
//                p2pConnectInfo.dev_name = node.getName();
//
//                /**
//                 * 免登陆模式下 node.node.sDevId是 String sDevId
//                 * =clientCore.encryptDevId
//                 * (String.valueOf(node.node.dwNodeId),info.pDevId, info.iChNo);
//                 */
//                String sDevId = node.node.sDevId;
//                p2pConnectInfo.dev_id = sDevId;
//                p2pConnectInfo.channel = info.iChNo;
//            }
//        }
//        return p2pConnectInfo;
//
//    }
//
//    /**
//     * 查询报警设置
//     *
//     * @param clientCore
//     * @param deviceId
//     * @param handler
//     * @param context
//     */
//    public static void queryAlarmSettings(ClientCore clientCore, String deviceId,
//                                          final Handler handler, final Context context) {
//        clientCore.queryAlarmSettings(deviceId, new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                ResponseQueryAlarmSettings responseQueryAlarmSettings = (ResponseQueryAlarmSettings) msg.obj;
//                if (responseQueryAlarmSettings != null
//                        && responseQueryAlarmSettings.h != null) {
//                    if (responseQueryAlarmSettings.h.e == 200) {
//                        LogUtil.i("查询报警布防成功！" + responseQueryAlarmSettings.b.toJsonString());
//                        handler.sendMessage(Message.obtain(handler, AppConst.LOAD_SUCCESS,
//                                responseQueryAlarmSettings.b.devs[0]));
//                    } else {
//                        // Log.e(WebSdkApi.WebSdkApi_Error,
//                        // "查询报警布防失败!code="
//                        // + responseQueryAlarmSettings.h.e);
//                        LogUtil.i("查询报警布防失败");
//                        handler.sendEmptyMessage(AppConst.LOAD_FAIL);
//
//                    }
//                } else {
//                    // Log.e(WebSdkApi.WebSdkApi_Error,
//                    // "查询报警布防失败! error=" + msg.what);
//                    LogUtil.i("查询报警布防失败");
//                    handler.sendEmptyMessage(AppConst.LOAD_FAIL);
//                }
//                super.handleMessage(msg);
//            }
//        });
//    }
//
//    /**
//     * 查询全部报警
//     *
//     * @param clientCore
//     * @param handler
//     */
//    public static void queryAllAlarmList(final ClientCore clientCore, final Handler handler) {
//        clientCore.queryAlarmList(new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                ResponseQueryAlarm responseQueryAlarm = (ResponseQueryAlarm) msg.obj;
//                if (responseQueryAlarm != null && responseQueryAlarm.h != null) {
//                    if (responseQueryAlarm.h.e == 200) {
//                        if (responseQueryAlarm.b != null
//                                && responseQueryAlarm.b.alarms != null) {
//                            for (int i = 0; i < responseQueryAlarm.b.alarms.length; i++) {
//                                /*Log.i("alarms", responseQueryAlarm.b.alarms[i]
//                                        .toString());*/
//                                LogUtil.i(responseQueryAlarm.b.alarms[i].toString());
//                            }
//                            LogUtil.i("SDK发送报警成功");
//                            handler.sendMessage(Message.obtain(handler,
//                                    AppConst.QUERY_ALARM_S, responseQueryAlarm));
//                        } else {
//                            LogUtil.i("SDK其它错误");
//                            handler.sendEmptyMessage(AppConst.QUERY_ALARM_F);
//                        }
//
//                    } else {
//                        LogUtil.i(" 查询报警失败!code=" + responseQueryAlarm.h.e);
//                        handler.sendEmptyMessage(AppConst.QUERY_ALARM_F);
//                    }
//                } else {
//                    LogUtil.i("查询报警失败! error=" + msg.what);
//                    handler.sendEmptyMessage(AppConst.QUERY_ALARM_F);
//                }
//                super.handleMessage(msg);
//            }
//
//        });
//
//    }
//
//
//    /**
//     * 指定设备查询报警
//     *
//     * @param clientCore
//     * @param handler
//     */
//    public static void queryAlarm(final int page_index, final int page_size, final String user_id, final String dev_id, final int[] alarm_events, final String start_time, final String end_time, final ClientCore clientCore, final Handler handler) {
//        clientCore.queryAlarm(page_index, page_size, user_id, dev_id, alarm_events, start_time, end_time, new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                ResponseQueryAlarm responseQueryAlarm = (ResponseQueryAlarm) msg.obj;
//                if (responseQueryAlarm != null && responseQueryAlarm.h != null) {
//                    if (responseQueryAlarm.h.e == 200) {
//                        if (responseQueryAlarm.b != null
//                                && responseQueryAlarm.b.alarms != null) {
//                            for (int i = 0; i < responseQueryAlarm.b.alarms.length; i++) {
//                                LogUtil.i(responseQueryAlarm.b.alarms[i].toString());
//                            }
//                            handler.sendMessage(Message.obtain(handler,
//                                    AppConst.QUERY_ALARM_S, responseQueryAlarm));
//                        } else {
//                            LogUtil.i("其它错误");
//                            handler.sendEmptyMessage(AppConst.QUERY_ALARM_F);
//                        }
//
//                    } else {
//                        LogUtil.i(" 查询报警失败!code=" + responseQueryAlarm.h.e);
//                        handler.sendEmptyMessage(AppConst.QUERY_ALARM_F);
//                    }
//                } else {
//                    LogUtil.i("查询报警失败! error=" + msg.what);
//                    handler.sendEmptyMessage(AppConst.QUERY_ALARM_F);
//                }
//                super.handleMessage(msg);
//            }
//
//        });
//
//    }
//
//    /**
//     * 查询某时间段，某类型 报警记录
//     *
//     * @param id
//     * @param type     报警类型
//     * @param start    开始时间
//     * @param end      结束时间
//     * @param listener 加载监听
//     * @return
//     */
//    public static void getAlarmList(String id, int type, String start,
//                                    String end, ClientCore clientCore, final LoadListener listener) {
//        final List<MessageInfo> list = new ArrayList<MessageInfo>();
//        if (start == null || end == null || id.equals("")) {
//            if (id.equals("")) {// 查询所有报警信息
//                queryAllAlarmList(clientCore, new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                        switch (msg.what) {
//                            case AppConst.QUERY_ALARM_S:
//                                list.clear();
//                                ResponseQueryAlarm responseQueryAlarm = (ResponseQueryAlarm) msg.obj;
//                                for (int i = 0; i < responseQueryAlarm.b.alarms.length; i++) {
//                                    AlarmInfo info = responseQueryAlarm.b.alarms[i];
//                                    MessageInfo messageInfo = new MessageInfo(
//                                            info.alarm_event, info.alarm_id,
//                                            info.alarm_info, info.alarm_state,
//                                            info.alarm_time, info.dev_id);
//                                    list.add(messageInfo);
//                                }
//                                if (list.size() > 0) {
//                                    listener.onSuccess(list);
//                                } else {
//                                    listener.onFail();
//                                }
//                                break;
//                            case AppConst.QUERY_ALARM_F:
//                                listener.onFail();
//                                break;
//
//                            default:
//                                listener.onFail();
//                                break;
//                        }
//                    }
//                });
//            }
//        } else {// 根据时间，id，类型查询报警
//            int page_index = 0;
//            if (type == 0) {
//                querySpecificAlarm(list, listener, id, null, start, end,
//                        clientCore);
//            } else {
//                int[] types = new int[1];
//                types[0] = type;
//                querySpecificAlarm(list, listener, id, types, start, end,
//                        clientCore);
//            }
//        }
//    }
//
//    /*
//     * 根据时间，id，类型查询报警
//     */
//    public static void querySpecificAlarm(final List<MessageInfo> list,
//                                          final LoadListener listener, String dev_id, int[] alarm_events,
//                                          String start_time, String end_time, ClientCore clientCore) {
//        queryAlarm(0, 0, " ", dev_id, alarm_events, start_time, end_time,
//                clientCore, new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                        switch (msg.what) {
//                            case AppConst.QUERY_ALARM_S:
//                                list.clear();
//                                ResponseQueryAlarm responseQueryAlarm = (ResponseQueryAlarm) msg.obj;
//                                for (int i = 0; i < responseQueryAlarm.b.alarms.length; i++) {
//                                    AlarmInfo info = responseQueryAlarm.b.alarms[i];
//                                    MessageInfo messageInfo = new MessageInfo(
//                                            info.alarm_event, info.alarm_id,
//                                            info.alarm_info, info.alarm_state,
//                                            info.alarm_time, info.dev_id);
//                                    list.add(messageInfo);
//                                }
//                                if (list.size() > 0) {
//                                    listener.onSuccess(list);
//                                } else {
//                                    listener.onFail();
//                                }
//                                break;
//                            case AppConst.QUERY_ALARM_F:
//                                listener.onFail();
//                                break;
//
//                            default:
//                                listener.onFail();
//                                break;
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 删除全部报警
//     *
//     * @param clientCore
//     * @param handler
//     */
//    public static void deleteAllInfo(final ClientCore clientCore, final Handler handler) {
//        clientCore.deleteAllAlarm(new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                // TODO Auto-generated method stub
//                ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                if (responseCommon != null
//                        && responseCommon.h != null) {
//                    if (responseCommon.h.e == 200) {
//                        handler.sendEmptyMessage(AppConst.DELETE_ALARM_S);
//                    } else {
//                        LogUtil.i("删除所有报警信息失败!code=" + responseCommon.h.e);
//                        handler.sendEmptyMessage(AppConst.DELETE_ALARM_F);
//                    }
//                } else {
//                    LogUtil.i("删除所有报警信息失败! error=" + msg.what);
//                    handler.sendEmptyMessage(AppConst.DELETE_ALARM_F);
//                }
//                super.handleMessage(msg);
//            }
//        });
//    }
//
//    /**
//     * 根据id删除报警信息
//     *
//     * @param clientCore
//     * @param alarm_id
//     */
//    public static void deleteAlarmInfo(final ClientCore clientCore, final String alarm_id) {
//        clientCore.deleteAlarm(alarm_id, new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                if (responseCommon != null
//                        && responseCommon.h != null) {
//                    if (responseCommon.h.e == 200) {
//                        LogUtil.i("删除id报警成功");
//                    } else {
//                        LogUtil.i("删除所有报警信息失败!code=" + responseCommon.h.e);
//                    }
//                } else {
//                    LogUtil.i("删除所有报警信息失败! error=" + msg.what);
//                }
//                super.handleMessage(msg);
//            }
//        });
//    }
//
//    /**
//     * 根据客户端定制标识获取服务器列表
//     *
//     * @param clientCore 暂时未用
//     */
//    public static void getServerList(final Context context,
//                                     final ClientCore clientCore) {
//        clientCore.getServerList(new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                // TODO Auto-generated method stub
//                final ResponseGetServerList responseGetServerList = (ResponseGetServerList) msg.obj;
//                if (responseGetServerList != null
//                        && responseGetServerList.h != null) {
//                    if (responseGetServerList.h.e == 200
//                            && responseGetServerList.b.srvs != null) {
//                        ServerListInfo serverListInfos[] = responseGetServerList.b.srvs;
//                        for (int i = 0; i < serverListInfos.length; i++) {
//
//                            LogUtil.i("" + serverListInfos[i].toString());
//                        }
//
//                    } else {
//                        LogUtil.i("获取服务器列表失败! code=" + responseGetServerList.h.e);
//                    }
//                } else {
//                    LogUtil.i("获取服务器列表失败! error=" + msg.what);
//                }
//
//                super.handleMessage(msg);
//            }
//
//        });
//    }
//
//    /**
//     * 远程录像搜索
//     *
//     * @param deviceId     设备id
//     * @param startTime    开始时间
//     * @param endTime      结束时间
//     * @param fileType     事件类型
//     * @param crrpActivity 上下文
//     * @return
//     */
//    public static List<TVideoFile> searchVideo(String deviceId,
//                                               Date_Time startTime, Date_Time endTime, int fileType,
//                                               Context crrpActivity, int recordStream) {
//        List<TVideoFile> data = new ArrayList<TVideoFile>();
//        int ret = 0;
//        PlayerQueryFile playerQueryFile = new PlayerQueryFile();
//        PlayerSearchCore SearchCore = new PlayerSearchCore(crrpActivity);
//        if (0 == recordStream) {
//            ret = SearchCore.SearchRecFileEx(deviceId, startTime, endTime, fileType);
//        } else {
//            switch (fileType) { // 子码流时和主码流回放判断录像类型的值不同，这里加以转换
//                case 255:
//                    fileType = PlayerQueryFile.NPC_D_MON_FILE_CREATE_MODE_ALL;
//                    break;
//                case 1:
//                    fileType = PlayerQueryFile.NPC_D_MON_FILE_CREATE_MODE_TIMER;
//                    break;
//                case 4:
//                    fileType = PlayerQueryFile.NPC_D_MON_FILE_CREATE_MODE_MANUAL;
//                    break;
//                case 2:
//                    fileType = PlayerQueryFile.NPC_D_MON_FILE_CREATE_MODE_ALARM;
//                    break;
//
//                default:
//                    break;
//            }
//
//            ret = playerQueryFile.QueryFile(deviceId, recordStream, startTime,
//                    endTime, PlayerQueryFile.NPC_D_MON_FILE_TYPE_RECORD,
//                    // PlayerQueryFile.NPC_D_MON_FILE_CREATE_MODE_ALL,
//                    fileType, PlayerQueryFile.NPC_D_MON_ALARM_EVENT_ALL);
//        }
//        if (ret > 0) {
//            while (true) {
//                TVideoFile videoFile;
//                if (0 == recordStream) {
//                    videoFile = SearchCore.GetNextRecFile();
//                } else {
//                    videoFile = playerQueryFile.GetNextRecFile();
//                }
//                if (videoFile == null) {
//                    break;
//                } else {
//                    data.add(Copy(videoFile));
//                }
//            }
//            playerQueryFile.Release();
//            SearchCore.Release();
//            // fix bug:9606
//        } else {
//            if (ret == -111) {
//                AppConst.isHaveAccess = false;
//            } else {
//                AppConst.isHaveAccess = true;
//            }
//        }
//        return data;
//    }
//
//    // 搜索图片文件
//    public static List<TVideoFile> searchPicFile(String deviceId,
//                                                 Date_Time startTime, Date_Time endTime, Context crrpActivity) {
//        List<TVideoFile> data = new ArrayList<TVideoFile>();
//        PlayerQueryFile playerQueryFile = new PlayerQueryFile();
//        int ret = playerQueryFile.QueryFile(deviceId, 0, startTime, endTime,
//                PlayerQueryFile.NPC_D_MON_FILE_TYPE_PICTRUE,
//                PlayerQueryFile.NPC_D_MON_FILE_CREATE_MODE_ALL,
//                PlayerQueryFile.NPC_D_MON_ALARM_EVENT_ALL);
//        if (ret > 0) {
//            while (true) {
//                TVideoFile picfile = playerQueryFile.GetNextRecFile();
//                if (picfile == null) {
//                    break;
//                } else {
//                    data.add(Copy(picfile));
//                }
//            }
//        } else {
//            // fix bug:9606
//            if (ret == -111) {
//                AppConst.isHaveAccess = false;
//            } else {
//                AppConst.isHaveAccess = true;
//            }
//        }
//        playerQueryFile.Release();
//
//        return data;
//    }
//
//    public static List<TDownFrame> searchPic(String deviceId,
//                                             Date_Time startTime, Date_Time endTime, int fileType,
//                                             Context crrpActivity) {
//        List<TDownFrame> data = new ArrayList<TDownFrame>();
//        PlayerSearchCore SearchCore = new PlayerSearchCore(crrpActivity);
//        int ret = SearchCore.SearchRecFileEx(deviceId, startTime, endTime,
//                PlayerSearchCore.NPC_D_MON_REC_FILE_TYPE_PIC_ALL);
//        if (ret > 0) {
//            PlayerDownFileCore playerDownFileCore = new PlayerDownFileCore(
//                    deviceId);
//            SearchCore.Release();
//            // fix bug:9606
//        } else {
//            if (ret == -111) {
//                AppConst.isHaveAccess = false;
//            } else {
//                AppConst.isHaveAccess = true;
//            }
//        }
//        return data;
//    }
//
//    /**
//     * 录像文件拷贝
//     *
//     * @param v1
//     * @return
//     */
//    public static TVideoFile Copy(TVideoFile v1) {
//        TVideoFile v2 = new TVideoFile();
//        v2.Channel = v1.Channel;
//        v2.eday = v1.eday;
//        v2.ehour = v1.ehour;
//        v2.eminute = v1.eminute;
//        v2.emonth = v1.emonth;
//        v2.eminute = v1.eminute;
//        v2.esecond = v1.esecond;
//        v2.eyear = v1.eyear;
//        v2.FileName = v1.FileName;
//        v2.nFileSize = v1.nFileSize;
//        v2.nFileType = v1.nFileType;
//        v2.nParam1 = v1.nParam1;
//        v2.nParam2 = v1.nParam2;
//        v2.sday = v1.sday;
//        v2.shour = v1.shour;
//        v2.sminute = v1.sminute;
//        v2.smonth = v1.smonth;
//        v2.ssecond = v1.ssecond;
//        v2.syear = v1.syear;
//        return v2;
//    }
//
//    public static void getEmailName(final Context context,
//                                    final String userName, final ClientCore clientCore,
//                                    final Handler handler) {
//        clientCore.queryUserInfo(context, userName, new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                ResponseQueryUserInfo responseQueryUserInfo = (ResponseQueryUserInfo) msg.obj;
//                if (responseQueryUserInfo != null
//                        && responseQueryUserInfo.h != null) {
//                    if (responseQueryUserInfo.h.e == 200) {
//                        LogUtil.i("查询用户信息成功！\n" + responseQueryUserInfo.b.toJsonString());
//                        String email = responseQueryUserInfo.b.email;
//                        handler.sendMessage(Message.obtain(handler,
//                                AppConst.GET_EMAIL_S, email));
//                    } else if (responseQueryUserInfo.h.e == 406) {
//                        handler.sendEmptyMessage(AppConst.GET_EMAIL_F);
//                        LogUtil.i("查询用户失败! 用户名错误,");
//                    } else {
//                        handler.sendEmptyMessage(AppConst.GET_EMAIL_F);
//                        LogUtil.i("查询用户信息失败，error=" + responseQueryUserInfo.h.e);
//                    }
//                } else {
//                    handler.sendEmptyMessage(AppConst.GET_EMAIL_F);
//                    LogUtil.i("查询用户信息失败");
//                }
//            }
//        });
//    }
//
}
