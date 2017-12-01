package me.jessyan.mvparms.demo.app;

/**
 * Created by czw on 2017/11/30.
 */
public class ApiConfiguration {

    // 编译运行模式
    public static final class RunnModel {
        //debug模式，会开启测试数据
        public static final boolean DEBUG = true;
    }

    public static final class Domain {
        /*域名*/
        public static final String BASE_HOST = RunnModel.DEBUG ? "http://rcktest.woyes.com" : "http://rck.woyes.com";

        public static final String CARD_BASE_URL = BASE_HOST + "/apiComponent/api/client";
    }

    // 请求的方法名称方法
    public static final class Method {
        //--------------------------首页 Start------------------------
        public static final String MENU = "getPageByType";//  获取页面布局
    }

}
