package com.jess.arms.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jess on 2015/11/27.
 */
public abstract class DefaultAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {
    protected List<T> mInfos;
    protected OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private BaseHolder<T> mHolder;

    public DefaultAdapter(List<T> infos) {
        super();
        this.mInfos = infos;
    }

    /**
     * 创建Hodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        mHolder = getHolder(view, viewType);
        mHolder.setOnItemClickListener(new BaseHolder.OnViewClickListener() {//设置Item点击事件
            @Override
            public void onViewClick(View view, int position) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, viewType, mInfos.get(position), position);
                }
            }
        });
        return mHolder;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseHolder<T> holder, int position) {
        holder.setData(mInfos.get(position), position);
    }


    /**
     * 数据的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mInfos.size();
    }


    public List<T> getInfos() {
        return mInfos;
    }

    /**
     * 获得item的数据
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        return mInfos == null ? null : mInfos.get(position);
    }

    /**
     * 子类实现提供holder
     *
     * @param v
     * @param viewType
     * @return
     */
    public abstract BaseHolder<T> getHolder(View v, int viewType);

    /**
     * 提供Item的布局
     *
     * @param viewType
     * @return
     */
    public abstract int getLayoutId(int viewType);






    /**
     * 在item不可见时会调用此方法,可以在这个地方释放一些holder需要释放的资源
     *
     * @param holder
     */
    @Override
    public void onViewDetachedFromWindow(BaseHolder<T> holder) {
        holder.onRelease();
    }

    @Override
    public void onViewRecycled(BaseHolder<T> holder) {

    }

    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View view, int viewType, T data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
