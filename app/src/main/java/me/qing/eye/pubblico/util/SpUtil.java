package me.qing.eye.pubblico.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.qing.eye.pubblico.app.MyApp;

/**
 * SpUtil
 * <p>
 * Created by QING on 2017/10/22.
 * <p>
 * 重新学习
 */
public class SpUtil {

    private static final String SP_NAME = "config";
    private static SpUtil sInstance;
    private Context mContext;
    private Editor editor;
    private SharedPreferences sp;

    private SpUtil() {
    }

    private SpUtil(Context context) {
        this.mContext = context;
    }


    public static SpUtil getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SpUtil.class) {
                if (sInstance == null) {
                    sInstance = new SpUtil(context);
                }
            }

        }
        return sInstance;
    }

    public static SpUtil getsInstance() {
        if (sInstance == null) {
            synchronized (SpUtil.class) {
                if (sInstance == null) {
                    sInstance = new SpUtil(MyApp.getInstance());
                }
            }
        }
        return sInstance;
    }

    // TODO: 2017/10/22  MODE_PRIVATE 作用？

    private SharedPreferences getSP() {
        if (sp == null) {
            sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }

    private Editor getEditor() {
        if (editor == null) {
            editor = getSP().edit();
        }
        return editor;
    }

    private void setValue(String key, String value) {
        getEditor().putString(key, value);
        getEditor().commit();
    }

    public void clear() {
        getEditor().clear();
        getEditor().commit();
    }

    public void remove(String key) {
        getEditor().remove(key);
        getEditor().commit();
    }

    private void setBooleanValue(String key, boolean value) {
        getEditor().putBoolean(key, value);
        getEditor().commit();
    }

    private boolean getBooleanValue(String key) {
        return getSP().getBoolean(key, false);

    }

    private void setIntValue(String key, int value) {
        getEditor().putInt(key, value);
        getEditor().commit();
    }

    private int getIntValue(String key) {
        return getSP().getInt(key, -1);
    }

    private String getValue(String key) {
        return getSP().getString(key, "");

    }

    // ------------------------------------------------用户资料----------------------------------------------

    /**
     * 保存个推服务所识别的设备端
     *
     * @return
     */
    public String getClientid() {
        return getValue("clientId");
    }

    public void setClientid(String clientId) {
        setValue("clientId", clientId);
    }
    /* 记录用户上次操作 begin */

    /**
     * 序列化复杂数据为字符串
     *
     * @param object
     * @return str
     */
    public String serialize(Object object) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        String str = "";
        try {
            objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(object);
            //str= arrayOutputStream.toString();
            //str = URLEncoder.encode(str, "UTF-8");
            str = new String(Base64.encode(
                    arrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            arrayOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 反序列化数据
     *
     * @param str
     * @return
     */
    public Object deSerialize(String str) {
        Object object = null;
        try {
            byte[] readStr = Base64.decode(str, Base64.DEFAULT);
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(
                    readStr);
            ObjectInputStream objectInputStream = new ObjectInputStream(arrayInputStream);
            object = objectInputStream.readObject();
            objectInputStream.close();
            arrayInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    //获取下载的最新的版本信息
    public String getAudioList() {
        return getValue(getUserName() + "audiolist");
    }

    public void setAudioList(String audioList) {
        setValue(getUserName() + "audiolist", audioList);
    }

    public String getDeviceMessage() {
        return getValue(getUserName() + "devicemessage");
    }

    public void setDeviceMessage(String deviceMessage) {
        setValue(getUserName() + "devicemessage", deviceMessage);
    }

    //获取下载的最新的版本信息
    public boolean getisStopAll() {
        return getBooleanValue(getUserName() + "isstopall");
    }

    public void setisStopAll(boolean isStopAll) {
        setBooleanValue(getUserName() + "isstopall", isStopAll);
    }

    public int getCurrentWindowNum() {
        return getIntValue(getUserName() + "windownum");
    }

    public void setCurrentWindowNum(int windowNum) {
        setIntValue(getUserName() + "windownum", windowNum);
    }

	/* end */

    /*是否需要版本更新begin*/
    public boolean getIsNeedVersionUpdate() {
        return getBooleanValue("IsNeedVersionUpdate");
    }

    public void setIsNeedVersionUpdate(boolean IsNeedVersionUpdate) {
        setBooleanValue("IsNeedVersionUpdate", IsNeedVersionUpdate);
    }

    //获取下载的最新的版本信息
    public String getApkName() {
        return getValue("apkname");
    }

    public void setApkName(String apkname) {
        setValue("apkname", apkname);
    }

    public String getVersionName() {
        return getValue("versionname");
    }

    public void setVersionName(String versionname) {
        setValue("versionname", versionname);
    }

    public String getVersionTime() {
        return getValue("versiontime");
    }

    public void setVersionTime(String versiontime) {
        setValue("versiontime", versiontime);
    }

    public String getVersionFeature() {
        return getValue("versionfeature");
    }

    public void setVersionFeature(String versionfeature) {
        setValue("versionfeature", versionfeature);
    }

    public String getApkSize() {
        return getValue("apksize");
    }

    public void setApkSize(String apksize) {
        setValue("apksize", apksize);
    }

	/*end*/

    public String getIsFirstIn() {
        return getValue("isFirstIn");
    }

    public void setIsFirstIn(String isFirstIn) {
        setValue("isFirstIn", isFirstIn);
    }

    public String getMobile() {
        return getValue("mobile");
    }

    public void setMobile(String mobile) {
        setValue("mobile", mobile);
    }

    public String getUserName() {
        return getValue("username");
    }

    public void setUserName(String username) {
        setValue("username", username);
    }

    /*fix bug:10029 begin 用来保存上次非免登陆的用户名*/
    public String getLocalUserName() {
        return getValue("localUsername");
    }

    public void setLocalUserName(String username) {
        setValue("localUsername", username);
    }
	/*fix bug:10029 end*/

    public String getPassword() {
        return getValue("password");
    }

    public void setPassword(String password) {
        setValue("password", password);
    }

    /*fix bug:10194 begin 保存当前界面editview的用户名，密码*/
    public String getEditViewPassword() {
        return getValue("eVpassword");
    }

    public void setEditViewPassword(String password) {
        setValue("eVpassword", password);
    }

    public String getEditViewName() {
        return getValue("eVname");
    }

    public void setEditViewName(String password) {
        setValue("eVname", password);
    }
	/*fix bug:10194 end*/

    public String getIsRemberPwd() {
        return getValue("isRemberPwd");
    }

    public void setIsRemberPwd(String isRemberPwd) {
        setValue("isRemberPwd", isRemberPwd);
    }

    public String getEmail() {
        return getValue("email");
    }

    public void setEmail(String email) {
        setValue("email", email);
    }

    public void setImageDir(String imageDir) {
        setValue("imageDir", imageDir);
    }

    public String getImageDir() {
        return getValue("imageDir");
    }

    public void setVideoDir(String videoDir) {
        setValue("videoDir", videoDir);
    }

    public String getVideoDir() {
        return getValue("videoDir");
    }

    public void setAlarmSwitch(boolean isOpen) {
        setBooleanValue("alarmSwitch", isOpen);
    }

    public boolean getAlarmSwitch() {
        return getBooleanValue("alarmSwitch");
    }

    public void setPushSwitch(boolean isOpen) {
        setBooleanValue("pushSwitch", isOpen);
    }

    public boolean getPushSwitch() {
        return getBooleanValue("pushSwitch");
    }

    public void setIsLogin(boolean isLogin) {
        setBooleanValue("isLogin", isLogin);
    }

    public boolean hasLogin() {
        return getBooleanValue("isLogin");
    }

    public void sethasLoadDevice(boolean hasLoadDevice) {
        setBooleanValue("hasLoadDevice", hasLoadDevice);
    }

    public boolean hasLoadDevice() {
        return getBooleanValue("hasLoadDevice");
    }

    // 保存用户是否点击了“记住密码”
    public void setRemenberPass(boolean isChecked) {
        setBooleanValue("rpIsChecked", isChecked);
    }

    public boolean getRemenberPass() {
        return getBooleanValue("rpIsChecked");
    }

    // 保存用户是否点击了“自动登录”
    public void setAutoLogin(boolean isChecked) {
        setBooleanValue("alIsChecked", isChecked);
    }

    public boolean getAutoLogin() {
        return getBooleanValue("alIsChecked");
    }

    /*bug 7721 zhengyin begin*/
    //保存用户是否点击了"注销"
    public void setIsClickedCancel(boolean isChecked) {
        setBooleanValue("isClicked", isChecked);
    }

    //获取用户是否点击了"注销"
    public boolean getIsClickedCancel() {
        return getBooleanValue("isClicked");
    }
	/*bug 7721 zhengyin end*/


    // 保存用户是否曾经登录成功过
    public void setUsedToLogin(boolean usedToLogin) {
        setBooleanValue("usedToLogin", usedToLogin);
    }

    public boolean getUsedToLogin() {
        return getBooleanValue("usedToLogin");
    }

    public void setImsi(String imsi) {
        setValue("imsi", imsi);
    }

    public String getImsi() {
        return getValue("imsi");
    }

    public void setConfigPath(String configPath) {
        setValue("configPath", configPath);
    }

    public String getConfigPath() {
        return getValue("configPath");
    }

    public boolean isLocalLogin() {
        return getBooleanValue("isLocalLogin");
    }

    public void setLocalLogin(boolean isLocalLogin) {
        setBooleanValue("isLocalLogin", isLocalLogin);
    }

    // 判断当前是否在注册界面
    public void setIsInRegist(boolean isInRegist) {
        setBooleanValue("isInRegist", isInRegist);
    }

    public boolean getIsInRegist() {
        return getBooleanValue("isInRegist");
    }

    //判断用户是否设置了密码保护

    public void setPasswordProtect(boolean hasPasswordProtect) {
        setBooleanValue("hasPasswordProtect", hasPasswordProtect);
    }

    public boolean getPasswordProtect() {
        return getBooleanValue("hasPasswordProtect");
    }

    public void setPasswordProtectNumber(String passwordProtect) {
        setValue("passwordProtect", passwordProtect);
    }

    public String getPasswordProtectNumber() {
        return getValue("passwordProtect");
    }

    //保存云台步长的值
    public void setCloudStep(int cloudStep) {
        setIntValue("cloudStep", cloudStep);
    }

    public int getCloudStep() {
        int step = getIntValue("cloudStep");
        if (step < 0) {
            step = 5;
        }
        return step;
    }

    //免打扰模式
    public void setVibration(boolean vibration) {
        setBooleanValue("vibration", vibration);
    }

    public boolean getVibration() {
        return getBooleanValue("vibration");
    }

    public void setNotificationSound(boolean notificationSound) {
        setBooleanValue("notificationSound", notificationSound);
    }

    public boolean getNotificationSound() {
        return getBooleanValue("notificationSound");
    }

    public void setSikpGuide(boolean skipGuide) {
        setBooleanValue("skipGuide", skipGuide);
    }

    public boolean getSkipGuide() {
        return getBooleanValue("skipGuide");
    }

    //记录云台步长界面中点击的位置x
    public void setPosition(int position) {
        setIntValue("position", position);
    }

    public int getPosition() {
        return getIntValue("position");
    }

    //保存服务器地址
    public void setServerAddress(String serverAddress) {
        setValue("serverAddress", serverAddress);
    }

    public String getServerAddress() {
        return getValue("serverAddress");
    }

    //保存服务器端口
    public void setServerPort(String serverPort) {
        setValue("serverPort", serverPort);
    }

    public String getServerPort() {
        return getValue("serverPort");
    }

    public void setNoDisturb(boolean noDisturb) {
        setBooleanValue("noDisturb", noDisturb);
    }

    public boolean getNoDisturb() {
        return getBooleanValue("noDisturb");
    }

//	//记录用户是否是自动登录状态
//	public void setIfAutoLog(boolean autoLog){
//		setBooleanValue("autoLog",autoLog );
//	}
//	public boolean getIfAutoLog(){
//		return getBooleanValue("autoLog");
//	}


}
