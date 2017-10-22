package me.qing.eye.pubblico.constants;


public class AppConst {
    public static boolean isFristLoadPreview = false;

    public static boolean isFromAlarmOrDeviceBrg = false; //是否点击报警信息或点击设备预览进入
    public static boolean isInPreviewOrPlaybackModule = false; //是否处于预览或回放模块
    //网络切换监听功能的实现
    public static boolean isClickedContinueInPreview = false; //弹出网络改变对话框点击”继续“
    public static boolean isCliekedCancel = false;   //弹出网络改变对话框点击”取消“
    public static boolean isClickedContinue = false; //弹出网络改变对话框点击”继续“
    //public static boolean isInPreviewModule = false; // 是否处于预览模式
    //public static boolean isInPlaybackModule = false; // 预览模块是否有弹出“移动网络”的权限

    public static final String appName = "vEye";
    public static boolean isStopVersionUpdate = false;
    public static int SCALE_NUM = 1;//scale numbers in one hour: 1:60min, 2:30min, 12:5min
    //fix bug :9606
    public static boolean isHaveAccess = true; //是否有访问文件的权限
    //server port去掉了原来的修饰符final,用户可以设置其值
//	public static  String server ="qvcloud.net";// 服务器  121.199.60.136
    public static String server = "api.qvcloud.net";// 服务器  121.199.60.136
    //	public static  int port = 8300;// 端口
    public static int port = 8888;// 端口
    public static int ip_port = 5801;
    public static boolean isFromHelpFrag = false;//用户是否点击了帮助界面
    // public static final String user = "test007";// 账户
    public static final String user = "hanhailin";// 账户
    public static final String password = "123456";// 密码
    public final static int NETWORK_ERROR = -2;// 登录成功
    public final static int LOGIN_OK_NO_DATA = 0;// 登录成功,没设备数据
    public static final byte NEW_DATA = 0x11;
    public static final byte DELETE_FAILED = 0x12;
    public static final byte DELETE_SUCCEED = 0x13;
    public static final byte ADD_FAILED = 0x14;
    public static final byte ADD_SUCCEED = 0x15;
    public static final String RECORDPATH = "videorecord";
    public static final int LOAD_START = 0;
    public static final int LOAD_ING = 4;
    public static final int LOAD_SUCCESS = 1;
    public static final int LOAD_FAIL = -1;
    public static final int LOAD_HALF = 2;
    public static final int LOAD_Littile_DATA = 3;
    public static int sHeight = -1;
    public static int sWidth = -1;
    public static boolean GetAlarmInfo = false;
    public static boolean isPcNeedStop = false;

    //标记当前fragment,在按下返回键时通过标记的值来做替换.
    public static boolean aboutFrg = false;
    public static boolean AlarmSettingFrg = false;
    public static boolean PTZFrg = false;
    public static boolean passprotectFrg = false;
    public static boolean passprotectSettingFrg = false;
    public static boolean modifypassprotectFrg = false;
    public static boolean cancelpassprotectFrg = false;
//	public static boolean setAutoLogin=false;


    //保存码流类型，包含了预览和回放，例如0表示预览主码流，回放主码流,1表示预览主码流，回放子码流。
    public static final int PreviewMainPlaybackMain = 0,
            PreviewMainPlaybackSub = 1, PreviewSubPlaybackMain = 2,
            PreviewSubPlaybackSub = 3, PreviewThirdPlaybackMain = 4, PreviewThirdPlaybackSub = 5;
    /**
     * 默认选中第一项
     */
//    public static ItemPosition selectedPos = new ItemPosition(0, 0, 0);

    public static String Device_Load_ACTION = "com.quvii.eye.deviceloadaction";

    public static final int ONE = 1;
    public static final int FOUR = 4;
    public static final int NINE = 9;
    public static final int SIXTEEN = 16;
    public static final int THIRTYTWO = 32;
    public static final int SIXTYFOUR = 64;
    public static final int MAXCHANNELNUM = SIXTYFOUR;
    public static int gridBottomPos = -1;
    public static int playImageViewWidth = -1;

    public static final int PLAYSTREAM_MAIN = 0;
    public static final int PLAYSTREAM_SUB = 1;


    public static final int PreviewMainStream = 0;
    public static final int PreviewSubStream = 1;
    public static final int PreviewThirdStream = 2;

    public static final int imageViewTileMode = 0;
    public static final int imageViewEqualProportionMode = 1;

    public final static int changePlayMode = 0x666;
    public final static byte SHOW_STATE = 0;
    public final static byte SHOW_ALL_STOP = 1;
    public final static int StopAllCompleted = 0x123;
    public final static int pcPrepared = 0x234;
    public final static int startPlay = 0x345;
    public final static int showPic = 0x456;
    public final static int searchEnd = 0x567;
    public static String downloadDir = null;

    public static String startTime, endTime;
    public static int rightMenuLastSelectedPosition;
    public static long AlarmInfoLastRefreshTime;
    public static long FileManageLastRefreshTime;
    public static int windowNum;
    // TODO: 2017/10/22  
//    public static List<Device> devs = new ArrayList<Device>();
    public static boolean AlarmPushClicked = false;

    /**
     * 客户端标识 ，由服务器生成，如需推送功能， 需后台绑定第三方推送的PUSH_APPID，PUSH_APPKEY，PUSH_APPSECRET。
     */
    public static final String custom_flag = "1000000065";
    public final static int LOGIN = 1;// 登录
    public final static int LOGIN_OK = 2;// 登录成功
    public final static int LOGIN_USER_OR_PWD_ERROR = -2;// 用户名或密码错误登录失败
    public final static int LUNCH_FAILED = -1;// 启动客户端失败
    public final static int LOGIN_FAILED = -3;// 其他登录失败
    public final static int MODIFY_PASSWORD_S = 3;// 修改密码成功
    public final static int MODIFY_PASSWORD_F = -4;// 修改密码失败
    public final static int REGIST_S = 5;// 注册成功
    public final static int REGIST_F = -5;// 注册失败
    public final static int LOGOUT_S = 6;// 注销成功
    public final static int LOGOUT_F = -6;// 注销失败
    public final static int GET_DEVLIST_F = 0;// 获取列表失败
    public final static int GET_DEVLIST_S = 7;// 获取列表成功
    public final static int GET_DEVLIST_OK_NO_DATA = 8;// 获取列表成功,没设备数据
    public final static int RESET_PASSWORD_S = 9;// 发送重置密码成功
    public final static int RESET_PASSWORD_F = -9;// 发送重置密码失败
    public final static int ADD_DEV_S = 10;// 添加设备成功
    public final static int ADD_DEV_F = -10;// 添加设备失败
    public final static int DELETE_DEV_S = 11;// 删除设备成功
    public final static int DELETE_DEV_F = -11;// 删除设备失败
    public final static int MODIFY_DEV_S = 12;// 修改设备成功
    public final static int MODIFY_DEV_F = -12;// 修改设备失败
    public final static int QUERY_ALARM_S = 13;// 查询报警成功
    public final static int QUERY_ALARM_F = -13;// 查询报警失败
    public final static int REGIST_F_USER_EXIST = 14;//注册用户已存在
    public final static int DELETE_ALARM_S = 15;// 删除报警成功
    public final static int DELETE_ALARM_F = -15;// 删除报警失败
    public final static int GET_EMAIL_S = 16;// 获取邮件成功
    public final static int GET_EMAIL_F = -16;// 获取邮件失败

    /*
     * SmartLight的命令集
     */
    public final static int QUERY_ABILITY = 0x07A0;
    public final static int SET_SMART_LIGHT = 0x07A1;
    public final static int QUERY_SMART_LIGHT = 0x07A2;

    /*
     * AlarmOutput的命令集
     */
    public final static int SET_ALARM_OUT = 0x07A3;
    public final static int QUERY_ALARM_OUT = 0x07A4;
    public final static int CAMERA_ALARMOUT_SET = 0;
    public final static int CAMERA_ALARMOUT_MANUAL = 1;
    public final static int CAMERA_ALARMOUT_OFF = 2;

    public static String CUSTOM_FLAG_NEUTRAL = "C0000"; // 客户代码，C0000为中性版本
    public static String[] CUSTOM_FLAG_LIST = {}; //定制客户代码表

    public final static int NPC_D_MON_REC_FILE_TYPE_ALL = 255;
    public final static int NPC_D_MON_REC_FILE_TYPE_GENERAL = 1;
    public final static int NPC_D_MON_REC_FILE_TYPE_ALARM = 2;
    public final static int NPC_D_MON_REC_FILE_TYPE_MALUAL = 4;

    public final static long SEARCH_PIC_LIMIT_TIME = 5 * 1000L;

    /**
     * 底部虚拟按键状态（适配三星s8）
     * 0 == 显示
     * 2 == 隐藏
     */
    public static int VIRTUAL_BAR_STATE = 0;

}