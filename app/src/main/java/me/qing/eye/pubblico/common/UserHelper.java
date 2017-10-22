//package me.qing.eye.pubblico.common;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.Player.Core.PlayerClient;
//import com.Player.Source.TAlarmSetInfor;
//import com.Player.web.response.ResponseCommon;
//import com.Player.web.websocket.ClientCore;
//import com.Player.web.websocket.FileUtils;
//import com.jess.arms.utils.LogUtil;
//import com.stream.NewAllStreamParser;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//
//import me.jessyan.mvparms.demo.R;
//import me.qing.eye.pubblico.constants.AppConst;
//import me.qing.eye.pubblico.data.api.SdkApi;
//import me.qing.eye.pubblico.util.SpUtil;
//
//import static com.Player.web.websocket.WebRequest.userName;
//import static com.igexin.sdk.GTServiceManager.context;
//
///**
// * 操作用户相关service实现类
// *
// * @author renjiang.yang
// *
// * UserManager
// */
//public class UserHelper {
//
//    private final static String userNameDir = "userNameDir.txt";
//    private final static String clientIdDir = "clientIdDir.txt";
//
//    private static UserHelper mInstance;
//    private static ClientCore mClientCore;
////    String sdCardPath = SdCard.getInstance().getSdCardPath();
//
//    private UserHelper() {
//        mClientCore = ClientCore.getInstance();
//    }
//
//    public static UserHelper getInstance() {
//        if (mInstance == null) {
//            synchronized (UserHelper.class) {
//                if (mInstance == null) {
//                    return mInstance = new UserHelper();
//                }
//            }
//        }
//        return mInstance;
//    }
//
//
//    /**
//     * 用户注册，sdkApi在工作线程中执行注册操作
//     *
//     * @param user 用户
//     */
//    public void userRegist(final User user, final LoadListener listener,
//                           final Context context) {
//        AndroidApplication ua = AndroidApplication.getInstance();
//        final PlayerClient pc = ua.getPc();
//        mClientCore = ClientCore.getInstance();
//        final Handler handler = new LoadHandler(listener);
//        // 注册开始
//        HandlerUtils.sendMsg(handler, AppConst.LOAD_START);
//        SdkApi.registeredUser(context, mClientCore, user.getUsername(),
//                user.getPwd(), user.getEmail(), "", "", "", new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        String des;
//                        switch (msg.what) {
//                            case AppConst.REGIST_S:
//                                HandlerUtils.sendMsg(handler,
//                                        AppConst.LOAD_SUCCESS);
//                                break;
//                            case AppConst.REGIST_F_USER_EXIST:
//                                des = context.getString(R.string.user_exist);
//                                HandlerUtils.sendMsg(handler,
//                                        AppConst.LOAD_FAIL, des);
//                                break;
//                            case AppConst.REGIST_F:
//                                des = context.getString(R.string.REGIST_FAIL);
//                                HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL,
//                                        des);
//                                break;
//                            default:
//                                des = context.getString(R.string.UNKNOWN_ERROR);
//                                HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL,
//                                        des);
//                                break;
//                        }
//                    }
//                });
//    }
//
//
//    public void userLogin(final String userName,final String pwd,boolean isLocalLogin){
//
//        // TODO: 2017/10/22
////        new WriteLogThread().start(); //dtz 20160301:华师SDK侧log,调试释放即可；
//
//        //1.获取ClientCore的实例  改成初始化的时候获取
//
//        //1.判断是否为免登录
//        if (isLocalLogin) {// 免登陆
//            mClientCore.setLocalList(true);
//        } else {// 用户登录
//            mClientCore.setLocalList(false);
//        }
//        //2.登录操作
//        SdkApi.loginServerAtUserId(mClientCore,userName,pwd,new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
////                String des;
//                switch (msg.what) {
//                    case AppConst.LOGIN:
//                        break;
//                    case AppConst.LOGIN_OK:
//                        NewAllStreamParser.isMD5 = true;
//                        if (!TextUtils.isEmpty(AlarmUtils.GETUI_CID)) {
//                            mClientCore.setUserPush(1, Utility.isZh(context) ? 2 : 1,
//                                    AlarmUtils.GETUI_CID, 1, 0, new Handler() {
//                                        @Override
//                                        public void handleMessage(Message msg) {
//                                            ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                                            if (responseCommon != null
//                                                    && responseCommon.h != null
//                                                    && responseCommon.h.e == 200) {
//                                                LogUtil.i("设置用户推送成功");
//                                            } else {
//                                                LogUtil.i("设置用户推送失败");
//                                            }
//                                        }
//                                    });
//                        }
//                        HandlerUtils.sendMsg(handler, AppConst.LOAD_SUCCESS);
//                        break;
//                    case AppConst.LOGIN_USER_OR_PWD_ERROR:
//                        des = context.getString(R.string.NPC_D_MPI_MON_ERROR_USER_PWD_ERROR);
//                        HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, des);
//                        break;
//                    case AppConst.LUNCH_FAILED:
//                        des = context.getString(R.string.loginfail);
//                        HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, des);
//                        break;
//                    case AppConst.LOGIN_FAILED:
//                        des = context.getString(R.string.connect_fail);
//                        HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, des);
//                        break;
//                    default:
//                        des = context.getString(R.string.UNKNOWN_ERROR);
//                        HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, des);
//                        break;
//                }
//            }
//        });
//
//
//    }
//
//    /**
//     * 用户登陆
//     *
//     * @param user 用户
//     * @throws UserException
//     */
//    public void userLogin(final User user, final boolean isLocalLogin,
//                          final LoadListener listener, final Context context)
//            throws UserException {
//
//        // 初始化数据及登录
//        mClientCore = ClientCore.getInstance();
//        final Handler handler = new LoadHandler(listener);
//        // 登录开始
//        handler.sendEmptyMessage(AppConst.LOAD_START);
//        /*关键参数*/
//        if (isLocalLogin) {// 免登陆
//            mClientCore.setLocalList(true);
//        } else {// 用户登录
//            mClientCore.setLocalList(false);
//        }
//        SdkApi.loginServerAtUserId(mClientCore, userName
//                user.getPwd(), new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        String des;
//                        switch (msg.what) {
//                            case AppConst.LOGIN:
//                                break;
//                            case AppConst.LOGIN_OK:
//                                NewAllStreamParser.isMD5 = true;
//                                if (!TextUtils.isEmpty(AlarmUtils.GETUI_CID)) {
//                                    mClientCore.setUserPush(1, Utility.isZh(context) ? 2 : 1,
//                                            AlarmUtils.GETUI_CID, 1, 0, new Handler() {
//                                                @Override
//                                                public void handleMessage(Message msg) {
//                                                    ResponseCommon responseCommon = (ResponseCommon) msg.obj;
//                                                    if (responseCommon != null
//                                                            && responseCommon.h != null
//                                                            && responseCommon.h.e == 200) {
//                                                        LogUtil.i("设置用户推送成功");
//                                                    } else {
//                                                        LogUtil.i("设置用户推送失败");
//                                                    }
//                                                }
//                                            });
//                                }
//                                HandlerUtils.sendMsg(handler, AppConst.LOAD_SUCCESS);
//                                break;
//                            case AppConst.LOGIN_USER_OR_PWD_ERROR:
//                                des = context.getString(R.string.NPC_D_MPI_MON_ERROR_USER_PWD_ERROR);
//                                HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, des);
//                                break;
//                            case AppConst.LUNCH_FAILED:
//                                des = context.getString(R.string.loginfail);
//                                HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, des);
//                                break;
//                            case AppConst.LOGIN_FAILED:
//                                des = context.getString(R.string.connect_fail);
//                                HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, des);
//                                break;
//                            default:
//                                des = context.getString(R.string.UNKNOWN_ERROR);
//                                HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, des);
//                                break;
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 通过用户名找回密码
//     *
//     * @param userName
//     */
//    public void findPassWord(final String userName,
//                             final LoadListener listener, final Context context) {
//        mClientCore = ClientCore.getInstance();
//        final Handler handler = new LoadHandler(listener);
//        // 注册查询
//        HandlerUtils.sendMsg(handler, AppConst.LOAD_START);
//        SdkApi.resetUserPassword(context, mClientCore, userName, 2,
//                new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//
//                        switch (msg.what) {
//                            case AppConst.RESET_PASSWORD_S:
//                                SdkApi.getEmailName(context, userName, mClientCore, new Handler() {
//                                    @Override
//                                    public void handleMessage(Message msg) {
//                                        super.handleMessage(msg);
//                                        switch (msg.what) {
//                                            case AppConst.GET_EMAIL_S:
//                                                HandlerUtils.sendMsg(handler,
//                                                        AppConst.LOAD_SUCCESS, msg.obj);
//                                                break;
//                                            case AppConst.GET_EMAIL_F:
//                                                String des = "";
//                                                HandlerUtils.sendMsg(handler,
//                                                        AppConst.LOAD_SUCCESS, des);
//                                                break;
//                                            default:
//                                                break;
//                                        }
//                                    }
//                                });
//                                break;
//                            case AppConst.RESET_PASSWORD_F:
//                                String des = context.getString(R.string.sent_fail);
//                                HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, des);
//                                break;
//                            default:
//                                String dess = context.getString(R.string.sent_fail);
//                                HandlerUtils.sendMsg(handler, AppConst.LOAD_FAIL, dess);
//                                break;
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 找回密码
//     *
//     * @param user 用户
//     * @throws UserException
//     */
//    public User findPassword(User user) throws UserException {
//        return null;
//    }
//
//
//
//    /**
//     * 显示是否推送通知到手机
//     */
//    public static boolean showIfNotify(TAlarmSetInfor alarmInfo) {
//        boolean isRun = false;
//        if (alarmInfo != null) {
//            String token = AlarmUtils.GETUI_CID;
//            Log.w("GETUI_CID.getToken", token + "");
//            if (alarmInfo != null && alarmInfo.notifies != null) {
//                for (int i = 0; i < alarmInfo.notifies.length; i++) {
//                    Log.w("alarmInfo", alarmInfo.notifies[i].notify_type + ","
//                            + alarmInfo.notifies[i].notify_param);
//                    if (alarmInfo.notifies[i].notify_type == PlayerClient.NPC_D_MON_ALARM_NOTIFY_TYPE_PHONE_PUSH
//                            && token.equals(alarmInfo.notifies[i].notify_param)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return isRun;
//    }
//
//
//    public static void writeToXML(User user) {
//        String str = "<root>\n<Item server=\"" + AppConst.server
//                + "\" username=\"" + user.getUsername() + "\" password=\""
//                + user.getPwd() + "\"" + " domain=\"" + AppConst.server + ":" + AppConst.port
//                + "\"" + " imsi=\"" + user.getImsi() + "\"" + " remember=\""
//                + "true" + "\"" + "/>\n" + "</root>";
//        try {
//            File localFile = new File(user.getConfigPath());
//            if (localFile.exists())
//                localFile.delete();
//            localFile.createNewFile();
//            new RandomAccessFile(localFile, "rw").write(str.getBytes(), 0,
//                    str.getBytes().length);
//        } catch (Exception localException) {
//            localException.printStackTrace();
//        }
//    }
//
//    /**
//     * 登录操作，如果用户已经登录就不进行任何操作，直接返回true
//     *
//     * @param context
//     * @return
//     */
//    public boolean login(final Context context) {
//        boolean isLogin = SpUtil.getInstance(context).hasLogin();
//        if (!isLogin) {
//            // 如果用户没执行登录操作就返回false
//            return false;
//        }
//        mClientCore = ClientCore.getInstance();
//
//        if (!mClientCore.IsLogin()) {// 如果没登录就先登录
//            final String username;
//            String pwd;
//            username = SpUtil.getInstance(context).getUserName();
//            pwd = SpUtil.getInstance(context).getPassword();
//            if (username.equals("nologinuser")) {
//                mClientCore.setLocalList(true);
//            } else {
//                mClientCore.setLocalList(false);
//            }
//            SdkApi.loginServerAtUserId(context, mClientCore, username, pwd, new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//
//                    switch (msg.what) {
//                        case AppConst.LOGIN:
//                            break;
//                        case AppConst.LOGIN_OK:
//                            if (!username.equals("nologinuser")) {
//                                SpUtil.getInstance(context)
//                                        .setIsLogin(true);
//                            }
//                            break;
//                        case AppConst.LOGIN_USER_OR_PWD_ERROR:
//                            SpUtil.getInstance(context).setIsLogin(false);
//                            break;
//                        case AppConst.LOGIN_FAILED:
//                            SpUtil.getInstance(context).setIsLogin(false);
//                            break;
//                        default:
//                            SpUtil.getInstance(context).setIsLogin(false);
//                            break;
//                    }
//                }
//            });
//            return SpUtil.getInstance(context).hasLogin();
//        } else {
//            return true;
//        }
//    }
//
//    /**
//     * 通过用户名查找用户
//     */
//    public User findByUserName(String name) throws UserException {
//        return null;
//    }
//
////	/**
////	 * bug7567
////	 * 判断当前的网络情况
////	 *
////	 */
////	 public boolean isNetworkAvailable(Activity activity)
////	    {
////	        Context context = activity.getApplicationContext();
////	        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
////	        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
////
////	        if (connectivityManager == null)
////	        {
////	            return false;
////	        }
////	        else
////	        {
////	            // 获取NetworkInfo对象
////	            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
////
////	            if (networkInfo != null && networkInfo.length > 0)
////	            {
////	                for (int i = 0; i < networkInfo.length; i++)
////	                {
////	                    // 判断当前网络状态是否为连接状态
////	                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
////	                    {
////	                        return true;
////	                    }
////	                }
////	            }
////	        }
////	        return false;
////	    }
//
//    public void WriteStringToFile(String filePath, String file) {
//        try {
//            File file1 = new File(filePath);
//            if (!file1.exists()) {
//                file1.createNewFile();
//            }
//            FileWriter fw = new FileWriter(filePath, false);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(file);
//            bw.close();
//            fw.close();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public String readStringFromFile(String filePath) {
//        String str = "";
//        try {
//            File file1 = new File(filePath);
//            FileReader fr = new FileReader(filePath);
//            BufferedReader br = new BufferedReader(fr);
//            str = br.readLine();
//            br.close();
//            fr.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return str;
//    }
//
//    public String readUserMess(Context context, int num) {
//
//        String loadMessDir = sdCardPath + "/" + context.getString(R.string.app_name)
//                + "/loadMessDir/";
//        String str = "";
//        if (0 == num) {
//            str = UserHelper.getInstance().readStringFromFile(loadMessDir + clientIdDir);
//        } else {
//            str = UserHelper.getInstance().readStringFromFile(loadMessDir + userNameDir);
//        }
//        return str;
//    }
//
//    public void writeUserMess(Context context, String str, int code) {
//        String loadMessDir = sdCardPath + "/" + context.getString(R.string.app_name)
//                + "/loadMessDir/";
//        FileUtils.mkdir(loadMessDir);
//        if (0 == code) {
//            UserHelper.getInstance().WriteStringToFile(
//                    loadMessDir + clientIdDir, str);
//        } else {
//            UserHelper.getInstance().WriteStringToFile(
//                    loadMessDir + userNameDir, str);
//        }
//
//    }
//}
