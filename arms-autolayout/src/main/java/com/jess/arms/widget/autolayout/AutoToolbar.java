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
package com.jess.arms.widget.autolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.utils.AutoLayoutHelper;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.autolayout.utils.DimenUtils;

import java.lang.reflect.Field;

/**
 * ================================================
 * 实现 AndroidAutoLayout 规范的 {@link Toolbar}
 * 可使用 MVP_generator_solution 中的 AutoView 模版生成各种符合 AndroidAutoLayout 规范的 {@link View}
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.6">AutoLayout wiki 官方文档</a>
 * Created by JessYan on 4/14/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AutoToolbar extends Toolbar {
    private static final int NO_VALID = -1;
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);
    private int mTextSize;
    private int mSubTextSize;

    public AutoToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar,
                defStyleAttr, R.style.Widget_AppCompat_Toolbar);

        int titleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance,
                R.style.TextAppearance_Widget_AppCompat_Toolbar_Title);

        int subtitleTextAppearance = a.getResourceId(R.styleable.Toolbar_subtitleTextAppearance,
                R.style.TextAppearance_Widget_AppCompat_Toolbar_Subtitle);

        mTextSize = loadTextSizeFromTextAppearance(titleTextAppearance);
        mSubTextSize = loadTextSizeFromTextAppearance(subtitleTextAppearance);

        a.recycle();
    }

    public AutoToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoToolbar(Context context) {
        this(context, null);
    }

    private int loadTextSizeFromTextAppearance(int textAppearanceResId) {
        TypedArray a = getContext().obtainStyledAttributes(textAppearanceResId,
                R.styleable.TextAppearance);
        try {
            if (!DimenUtils.isPxVal(a.peekValue(R.styleable.TextAppearance_android_textSize))) {
                return NO_VALID;
            }
            return a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, NO_VALID);
        } finally {
            a.recycle();
        }
    }

    private void setUpTitleTextSize() {
        CharSequence title = getTitle();
        if (!TextUtils.isEmpty(title) && mTextSize != NO_VALID) {
            setUpTitleTextSize("mTitleTextView", mTextSize);
        }
        CharSequence subtitle = getSubtitle();
        if (!TextUtils.isEmpty(subtitle) && mSubTextSize != NO_VALID) {
            setUpTitleTextSize("mSubtitleTextView", mSubTextSize);
        }
    }

    private void setUpTitleTextSize(String name, int val) {
        try {
            //反射 Toolbar 的 TextView
            Field f = getClass().getSuperclass().getDeclaredField(name);
            f.setAccessible(true);
            TextView textView = (TextView) f.get(this);
            if (textView != null) {
                int autoTextSize = AutoUtils.getPercentHeightSize(val);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, autoTextSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!this.isInEditMode()) {
            setUpTitleTextSize();
            this.mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.getContext(), attrs);
    }

    public static class LayoutParams extends Toolbar.LayoutParams implements AutoLayoutHelper.AutoLayoutParams {
        private AutoLayoutInfo mDimenLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.mDimenLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return this.mDimenLayoutInfo;
        }
    }
}
