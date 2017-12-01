package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.app.ApiConfiguration;
import me.jessyan.mvparms.demo.app.utils.NetworkUtils;
import me.jessyan.mvparms.demo.mvp.contract.MainContract;
import me.jessyan.mvparms.demo.mvp.model.entity.ConvenienceEntity;


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
    public Observable<List<ConvenienceEntity>> getMainMenu(Map<String, String> maps) {
        return NetworkUtils.requestObjectList(mRepositoryManager, ApiConfiguration.Domain.CARD_BASE_URL, maps, ConvenienceEntity[].class);
    }
}