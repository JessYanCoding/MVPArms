package me.jessyan.mvparms.demo.di.component;

import android.app.Application;

import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.ImageModule;
import com.jess.arms.widget.imageloader.ImageLoader;

import javax.inject.Singleton;

import dagger.Component;
import me.jessyan.mvparms.demo.di.module.ServiceModule;
import me.jessyan.mvparms.demo.mvp.model.api.service.ServiceManager;
import okhttp3.OkHttpClient;

/**
 * Created by jess on 8/4/16.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ServiceModule.class, ImageModule.class})
public interface AppComponent {
    Application Application();

    ServiceManager serviceManager();

    OkHttpClient okHttpClient();

    ImageLoader imageLoader();
}
