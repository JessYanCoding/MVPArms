package me.jessyan.mvparms.demo.app.utils.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author weijianxing
 *         <p/>
 *         封装JSON的操作
 *         <p/>
 *         优点:如果想更换JSON的库的话,只需要修改这个类就可以了
 */
public class FastJsonUtils {

    public static <T> T stringToObject(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }

    public static <T> List<T> stringToArrayList(String jsonString, Class<T> clazz) {
        return JSONArray.parseArray(jsonString, clazz);
    }

    public static JSONObject stringToObject(String jsonString) {
        return JSONArray.parseObject(jsonString);
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }
}
