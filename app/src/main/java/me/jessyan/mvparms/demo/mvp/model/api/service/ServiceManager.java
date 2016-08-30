package me.jessyan.mvparms.demo.mvp.model.api.service;

import com.jess.arms.http.BaseServiceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jess on 8/5/16 13:01
 * contact with jess.yan.effort@gmail.com
 */
@Singleton
public class ServiceManager extends BaseServiceManager {
    private CommonService mCommonService;

    @Inject public ServiceManager(CommonService commonService){
        this.mCommonService = commonService;
    }

    public CommonService getCommonService() {
        return mCommonService;
    }
}
