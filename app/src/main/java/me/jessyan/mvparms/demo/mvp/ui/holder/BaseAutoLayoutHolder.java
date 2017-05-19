package me.jessyan.mvparms.demo.mvp.ui.holder;

import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by xiaobailong24 on 2017/5/2.
 * BaseAutoLayoutHolder
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */

public class BaseAutoLayoutHolder extends BaseViewHolder {

    public BaseAutoLayoutHolder(View view) {
        super(view);
        AutoUtils.autoSize(view);
    }

    /**
     * {@link} https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/1053
     *
     * @param adapter
     * @return
     */
    @Override
    public BaseViewHolder setAdapter(BaseQuickAdapter adapter) {
        return super.setAdapter(adapter);
    }

    /**
     * Method: setHint
     * Description: 设置EditText的hint
     * Date: 2017/5/2 14:14
     */
    public BaseViewHolder setHint(int viewId, CharSequence value) {
        EditText view = getView(viewId);
        view.setHint(value);
        return this;
    }
}
