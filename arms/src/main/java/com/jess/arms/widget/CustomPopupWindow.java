package com.jess.arms.widget;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;


public class CustomPopupWindow extends PopupWindow {
    private View mContentView;
    private View mParentView;
    private CustomPopupWindowListener mListener;
    private boolean isOutsideTouch;
    private boolean isFocus;
    private Drawable mBackgroundDrawable;
    private int mAnimationStyle;
    private boolean isWrap;

    private CustomPopupWindow(Builder builder) {
        this.mContentView = builder.contentView;
        this.mParentView = builder.parentView;
        this.mListener = builder.listener;
        this.isOutsideTouch = builder.isOutsideTouch;
        this.isFocus = builder.isFocus;
        this.mBackgroundDrawable = builder.backgroundDrawable;
        this.mAnimationStyle = builder.animationStyle;
        this.isWrap = builder.isWrap;
        initLayout();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initLayout() {
        mListener.initPopupView(mContentView);
        setWidth(isWrap ? LayoutParams.WRAP_CONTENT : LayoutParams.MATCH_PARENT);
        setHeight(isWrap ? LayoutParams.WRAP_CONTENT : LayoutParams.MATCH_PARENT);
        setFocusable(isFocus);
        setOutsideTouchable(isOutsideTouch);
        setBackgroundDrawable(mBackgroundDrawable);
        if (mAnimationStyle != -1)//如果设置了动画则使用动画
            setAnimationStyle(mAnimationStyle);
        setContentView(mContentView);
    }

    /**
     * 获得用于展示popup内容的view
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 用于填充contentView,必须传ContextThemeWrapper(比如activity)不然popupwindow要报错
     * @param context
     * @param layoutId
     * @return
     */
    public static View inflateView(ContextThemeWrapper context, int layoutId) {
        return LayoutInflater.from(context)
                .inflate(layoutId, null);
    }

    public void show() {//默认显示到中间
        if (mParentView == null) {
            showAtLocation(mContentView, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            showAtLocation(mParentView, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }


    public static final class Builder {
        private View contentView;
        private View parentView;
        private CustomPopupWindowListener listener;
        private boolean isOutsideTouch = true;//默认为true
        private boolean isFocus = true;//默认为true
        private Drawable backgroundDrawable = new ColorDrawable(0x00000000);//默认为透明
        private int animationStyle = -1;
        private boolean isWrap;

        private Builder() {
        }

        public Builder contentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder parentView(View parentView) {
            this.parentView = parentView;
            return this;
        }

        public Builder isWrap(boolean isWrap) {
            this.isWrap = isWrap;
            return this;
        }


        public Builder customListener(CustomPopupWindowListener listener) {
            this.listener = listener;
            return this;
        }


        public Builder isOutsideTouch(boolean isOutsideTouch) {
            this.isOutsideTouch = isOutsideTouch;
            return this;
        }

        public Builder isFocus(boolean isFocus) {
            this.isFocus = isFocus;
            return this;
        }

        public Builder backgroundDrawable(Drawable backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder animationStyle(int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public CustomPopupWindow build() {
            if (contentView == null)
                throw new IllegalStateException("contentView is required");
            if (listener == null)
                throw new IllegalStateException("CustomPopupWindowListener is required");

            return new CustomPopupWindow(this);
        }
    }

    public interface CustomPopupWindowListener {
        public void initPopupView(View contentView);
    }

}
