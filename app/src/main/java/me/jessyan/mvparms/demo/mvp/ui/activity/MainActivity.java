package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.ClassDetails;

/**
 * ================================================
 * 主页面(MVC) 逻辑很简单不需要Presenter 不需要Dagger2注入对象
 * <p>
 * Created by Sum41forever 2018/1/15
 * ================================================
 */
public class MainActivity extends BaseActivity {

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    List<ClassDetails> mClassList;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.main_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClassDetails demo = (ClassDetails) adapterView.getAdapter().getItem(i);
                if (demo != null && demo.activityClass != null) {
                    startActivity(new Intent(getBaseContext(),
                            demo.activityClass));
                }
            }
        });
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
    }


}
