package com.jess.arms.common.utils;

import java.util.List;

/**
 * Created by huangzj on 2016/1/27.
 */
public class CollectionUtil {

    /**
     * 集合中是否存在指定元素
     *
     * @param value 指定字符
     * @param list  集合
     * @return
     */
    public static boolean existValue(String value, List<String> list) {
        if (list == null || value == null) {
            return false;
        }
        for (String str : list) {
            if (value.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
