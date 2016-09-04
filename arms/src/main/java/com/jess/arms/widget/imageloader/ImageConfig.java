package com.jess.arms.widget.imageloader;

import android.widget.ImageView;

/**
 * Created by jess on 8/5/16 15:19
 * contact with jess.yan.effort@gmail.com
 * 图片加载的配置信息
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholder;
    protected int errorPic;


    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}
