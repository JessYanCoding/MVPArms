/*
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.jess.arms.http.imageloader;

import android.widget.ImageView;

/**
 * Created by jess on 8/5/16 15:19
 * contact with jess.yan.effort@gmail.com
 * 这里是图片加载配置信息的基类,可以定义一些所有图片加载框架都可以用的通用参数
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
