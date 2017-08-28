package com.jess.arms.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.ExifInterface;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by jess on 2015/11/24.
 */
public class DrawableProvider {

    private DrawableProvider() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 获得选择器
     *
     * @param normalDrawable
     * @param pressDrawable
     * @return
     */
    public static Drawable getStateListDrawable(Drawable normalDrawable, Drawable pressDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, pressDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    /**
     * 将radiobutton的drawable动态的缩放
     *
     * @param percent
     * @param rb
     * @return
     */
    public static Drawable getScaleDrawableForRadioButton(float percent, TextView rb) {
        Drawable[] compoundDrawables = rb.getCompoundDrawables();
        Drawable drawable = null;
        for (Drawable d : compoundDrawables) {
            if (d != null) {
                drawable = d;
            }
        }
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * percent + 0.5f), (int) (drawable.getIntrinsicHeight() * percent + 0.5f));
        return drawable;
    }

    /**
     * 将radiobutton的drawable动态的缩放
     *
     * @param rb
     * @return
     */
    public static Drawable getScaleDrawableForRadioButton2(float width, TextView rb) {
        Drawable[] compoundDrawables = rb.getCompoundDrawables();
        Drawable drawable = null;
        for (Drawable d : compoundDrawables) {
            if (d != null) {
                drawable = d;
            }
        }
        float percent = width * 1.0f / drawable.getIntrinsicWidth();
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * percent + 0.5f), (int) (drawable.getIntrinsicHeight() * percent + 0.5f));
        return drawable;
    }

    /**
     * 传入图片，将图片按传入比例缩小
     *
     * @param percent
     * @return
     */
    public static Drawable getScaleDrawable(float percent, Drawable drawable) {
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * percent + 0.5f), (int) (drawable.getIntrinsicHeight() * percent + 0.5f));
        return drawable;
    }

    /**
     * 传入图片，将图片按传入比例缩小
     *
     * @param width
     * @return
     */
    public static Drawable getScaleDrawable2(float width, Drawable drawable) {
        float percent = width * 1.0f / drawable.getIntrinsicWidth();
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * percent + 0.5f), (int) (drawable.getIntrinsicHeight() * percent + 0.5f));
        return drawable;
    }

    /**
     * 设置左边的drawable
     *
     * @param tv
     * @param drawable
     */
    public static void setLeftDrawable(TextView tv, Drawable drawable) {
        tv.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 改变Bitmap的长宽
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getReSizeBitmap(Bitmap bitmap, float targetWidth, float targetheight) {
        Bitmap returnBm = null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(targetWidth / width, targetheight / height); //长和宽放大缩小的比例
        try {
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


}
