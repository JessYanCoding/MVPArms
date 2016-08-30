package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;

import com.jess.arms.utils.UiUtils;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.AppComponent;
import me.jessyan.mvparms.demo.mvp.model.entity.HomePicEntity;
import me.jessyan.mvparms.demo.mvp.ui.common.WEActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class MainActivity extends WEActivity {

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        appComponent
                .cacheManager()
                .getCommonCache()
                .getDailyList(appComponent
                        .serviceManager()
                        .getCommonService()
                        .getDailyList(),new DynamicKey("15"),new EvictDynamicKey(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Reply<HomePicEntity>>bindToLifecycle())
                .subscribe(new Action1<Reply<HomePicEntity>>() {
                    @Override
                    public void call(Reply<HomePicEntity> homePicEntityReply) {
                        UiUtils.makeText(homePicEntityReply.getData().getIssueList().get(0).getPublishTime()+"/"+homePicEntityReply.getSource().name());
                        Timber.tag(TAG).w(homePicEntityReply.getData().getIssueList().get(0).getPublishTime()+"");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main,null,false);
    }

    @Override
    protected void initData() {

    }
}
