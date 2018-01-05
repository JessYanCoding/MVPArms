package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.app.utils.net.NetworkUtils;
import me.jessyan.mvparms.demo.mvp.contract.MainContract;
import me.jessyan.mvparms.demo.mvp.model.entity.IndexMenuEntity;


@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<IndexMenuEntity> getMainMenu(Map<String, String> maps) {
        return NetworkUtils.requestToObject(mRepositoryManager, maps, IndexMenuEntity.class);
    }
}