package me.jessyan.mvparms.demo.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;

import butterknife.Bind;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import rx.Observable;

/**
 * Created by jess on 9/4/16 12:56
 * Contact with jess.yan.effort@gmail.com
 */
public class UserItemHolder extends BaseHolder<User> {

    @Nullable
    @Bind(R.id.iv_avatar)
    ImageView mAvater;
    @Nullable
    @Bind(R.id.tv_name)
    TextView mName;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private final WEApplication mApplication;

    public UserItemHolder(View itemView) {
        super(itemView);
        mApplication = (WEApplication) itemView.getContext().getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public void setData(User data) {
        Observable.just(data.getLogin())
                .subscribe(RxTextView.text(mName));

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .url(data.getAvatarUrl())
                .imagerView(mAvater)
                .build());
    }
}
