package me.jessyan.mvparms.demo.mvp.model.entity;

import android.app.Activity;

import java.io.Serializable;

/**
 * Description: 类信息实体
 *
 * @author Sum41forever 2018/1/15
 *         <a href="http://www.sum41forever.com/">Contact me</a>
 */
public class ClassDetails implements Serializable {

    private final String title;
    private final Class<? extends Activity> activityClass;

    public ClassDetails(String title, Class<? extends Activity> activityClass) {
        super();
        this.title = title;
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
