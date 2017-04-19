package com.jess.arms.common.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by lujianzhao on 16/3/31.
 */
public class ImageUtils {

    /**
     * 调整照片的色调,饱和度,亮度
     * MID_VALUE = 177; // 进度条的中间值
     * @param bm 原图
     * @param hue 色调 = (进度条进度 - MID_VALUE) * 1.0F / MIN_VALUE * 180
     * @param saturation 饱和度 = 进度条进度 * 1.0F / MIN_VALUE;
     * @param lum 亮度 = 进度条进度 * 1.0F / MIN_VALUE;
     * @return 调整后的图片
     */
    public static Bitmap handleImageEffect(Bitmap bm, int hue, int saturation, int lum) {
        Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0,hue);
        hueMatrix.setRotate(1,hue);
        hueMatrix.setRotate(2, hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum,lum,lum,1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm,0,0,paint);
        return bitmap;
    }
}
