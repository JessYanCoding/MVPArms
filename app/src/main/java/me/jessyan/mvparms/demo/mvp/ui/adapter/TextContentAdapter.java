package me.jessyan.mvparms.demo.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.TextContent;
import me.jessyan.mvparms.demo.mvp.ui.holder.BaseAutoLayoutHolder;

/**
 * Created by xiaobailong24 on 2017/5/15.
 * TextContentAdapter
 */

public class TextContentAdapter extends BaseQuickAdapter<TextContent, BaseAutoLayoutHolder> {
    public TextContentAdapter(@Nullable List<TextContent> data) {
        super(R.layout.text_content, data);
    }

    @Override
    public BaseAutoLayoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_content, parent, false);
        return new BaseAutoLayoutHolder(convertView);
    }

    @Override
    protected void convert(BaseAutoLayoutHolder helper, TextContent item) {
        helper.setText(R.id.content_title, item.getTitle())
                .setText(R.id.content_content, item.getContent());
    }
}
