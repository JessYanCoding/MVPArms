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
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.autolayout.utils.DimenUtils;

/**
 * ================================================
 * 实现 AndroidAutoLayout 规范的 {@link TabLayout}
 * 可使用 MVP_generator_solution 中的 AutoView 模版生成各种符合 AndroidAutoLayout 规范的 {@link View}
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.6">AutoLayout wiki 官方文档</a>
 * Created by JessYan on 4/14/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AutoTabLayout extends TabLayout {
    private static final int NO_VALID = -1;
    private int mTextSize;
    private boolean mTextSizeBaseWidth = false;

    public AutoTabLayout(Context context) {
        this(context, null);
    }

    public AutoTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initTextSizeBaseWidth(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayout,
                defStyleAttr, R.style.Widget_Design_TabLayout);
        int tabTextAppearance = a.getResourceId(R.styleable.TabLayout_tabTextAppearance,
                R.style.TextAppearance_Design_Tab);

        mTextSize = loadTextSizeFromTextAppearance(tabTextAppearance);
        a.recycle();
    }

    private void initTextSizeBaseWidth(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoTabLayout);
        mTextSizeBaseWidth = a.getBoolean(R.styleable.AutoTabLayout_auto_textSize_base_width, false);
        a.recycle();
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

    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);
        setUpTabTextSize(tab);
    }

    @Override
    public void addTab(@NonNull Tab tab, boolean setSelected) {
        super.addTab(tab, setSelected);
        setUpTabTextSize(tab);
    }

    private void setUpTabTextSize(Tab tab) {
        if (mTextSize == NO_VALID || tab.getCustomView() != null) {
            return;
        }

        ViewGroup tabGroup = (ViewGroup) getChildAt(0);
        ViewGroup tabContainer = (ViewGroup) tabGroup.getChildAt(tab.getPosition());
        TextView textView = (TextView) tabContainer.getChildAt(1);

        if (AutoUtils.autoed(textView)) {
            return;
        }
        int autoTextSize;
        if (mTextSizeBaseWidth) {
            autoTextSize = AutoUtils.getPercentWidthSize(mTextSize);
        } else {
            autoTextSize = AutoUtils.getPercentHeightSize(mTextSize);
        }

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, autoTextSize);
    }
}

