package com.jess.arms.mvp;

/**
 * Created by jess on 8/5/16 12:55
 * contact with jess.yan.effort@gmail.com
 */
public class BaseModel<T> {
    protected T mServiceManager;

    public BaseModel(T serviceManager) {
        this.mServiceManager = serviceManager;
    }
}
