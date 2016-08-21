package me.jessyan.mvparms.demo.mvp.model.api.service;

import me.jessyan.mvparms.demo.mvp.model.entity.FindDetailEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.FindMoreEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.HomePicEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.HotStrategyEntity;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by jess on 8/5/16 12:05
 * contact with jess.yan.effort@gmail.com
 */
public interface CommonService {

    @GET("/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<HomePicEntity> getDailyList();

    @GET("/api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<FindMoreEntity> getFindMore();

    @GET("/api/v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<HotStrategyEntity> getHotStrategy();

    @GET("/api/v3/videos?categoryName=%s&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<FindDetailEntity> getFindDetail();
}
