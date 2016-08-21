package me.jessyan.mvparms.demo.mvp.model.api.service;

/**
 * Created by jess on 8/5/16 13:01
 * contact with jess.yan.effort@gmail.com
 */
public class ServiceManager {
    private CommonService mCommonService;

    public ServiceManager(CommonService commonService){
        this.mCommonService = commonService;
    }

    public CommonService getCommonService() {
        return mCommonService;
    }
}
