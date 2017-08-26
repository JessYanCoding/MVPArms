package me.jessyan.mvparms.demo.mvp.ui.widget.update;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.mvparms.demo.R;

/**
 * app更新Dialog
 * Created by chan on 2017/8/26.
 */
public class UpdateDialogFragment extends DialogFragment {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.tv_ignore)
    TextView mTvIgnore;

    private Unbinder unbinder;
    private boolean isConstraint = false; //是否强制更新

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.UpdateAppDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        //点击window外的区域 是否消失
        getDialog().setCanceledOnTouchOutside(false);
        //是否可以取消,会影响上面那条属性
//        setCancelable(false);
//        //window外可以点击,不拦截窗口外的事件
//        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //禁用
                    if (isConstraint) {
                        //返回桌面
                        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        Window dialogWindow = getDialog().getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
            lp.height = (int) (displayMetrics.heightPixels * 0.8f);
            dialogWindow.setAttributes(lp);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update_app, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_close, R.id.btn_ok, R.id.tv_ignore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                ArmsUtils.makeText(view.getContext(),"正在下载应用...");
                break;
            case R.id.tv_ignore:
                ArmsUtils.makeText(view.getContext(),"忽略此版本");
                dismiss();
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }
}
