package me.jessyan.mvparms.demo.mvp.ui.widget.update;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
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
import com.jess.arms.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;
import me.jessyan.mvparms.demo.R;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadStatus;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static zlc.season.rxdownload2.function.Utils.log;

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
    @BindView(R.id.npb)
    NumberProgressBar mNpb;
    @BindView(R.id.tv_size)
    TextView mTvSize;
    @BindView(R.id.ll_close)
    AutoLinearLayout mLlClose;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_update_info)
    TextView mTvUpdateInfo;


    private Unbinder unbinder;
    private RxDownload mRxDownload;
    private boolean isConstraint = true; //是否强制更新
    public String url;
    public String versionCode;
    public String updateInfo;

    public UpdateDialogFragment() {

    }

    public static UpdateDialogFragment newInstance(String url, String versionCode, String updateInfo) {
        UpdateDialogFragment dialogFragment = new UpdateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("versionCode", versionCode);
        bundle.putString("updateInfo", updateInfo);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.UpdateAppDialog);
        Bundle args = getArguments();
        if (args != null) {
            this.url = args.getString("url");
            this.versionCode = args.getString("versionCode");
            this.updateInfo = args.getString("updateInfo");
        }
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
        mRxDownload = RxDownload.getInstance(getActivity()); //初始化RxDownload
    }

    //onResume状态 恢复下载记录
    @Override
    public void onResume() {
        super.onResume();
        mRxDownload.receiveDownloadStatus(url)
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(DownloadEvent downloadEvent) throws Exception {
                        if (downloadEvent.getFlag() == DownloadFlag.FAILED) {
                            Throwable throwable = downloadEvent.getError();
                            log(throwable);
                        }
                        updateEvent(downloadEvent);
                        updateProgress(downloadEvent);
                    }
                });
    }

    //下载监听事件
    private void updateEvent(DownloadEvent event) {
        int flag = event.getFlag();
        switch (flag) {
            case DownloadFlag.NORMAL: //未下载
                LogUtils.debugInfo("未下载");
                mTvTitle.setText("是否升级到" + versionCode + "版本？");
                mTvUpdateInfo.setText(updateInfo);
                break;
            case DownloadFlag.STARTED: //已开始下载
//                LogUtils.debugInfo("已开始下载");
                if (mBtnOk.getVisibility() == View.VISIBLE)
                    mBtnOk.setVisibility(View.GONE);
                if (mNpb.getVisibility() == View.GONE)
                    mNpb.setVisibility(View.VISIBLE);
                if (mLlClose.getVisibility() == View.VISIBLE)
                    mLlClose.setVisibility(View.GONE);
                break;
            case DownloadFlag.PAUSED: //已暂停
                LogUtils.debugInfo("已暂停");
                start();
                mBtnOk.setVisibility(View.GONE);
                mNpb.setVisibility(View.VISIBLE);
                break;
            case DownloadFlag.COMPLETED: //已完成
                LogUtils.debugInfo("已完成");
                ArmsUtils.makeText(getActivity(), "最新安装包已下载，请安装");
                installApk();
                break;
            case DownloadFlag.FAILED: //下载失败
                LogUtils.debugInfo("下载失败");
                ArmsUtils.makeText(getActivity(), "下载失败");
                delete(); //下载失败，删除文件
                dismiss();
                break;
        }
    }

    //刷新进度条
    private void updateProgress(DownloadEvent event) {
        DownloadStatus status = event.getDownloadStatus();
        mNpb.setMax(100);
        mNpb.setProgress((int) status.getPercentNumber());
    }

    //开始下载
    private void start() {
        new RxPermissions(getActivity())
                .request(WRITE_EXTERNAL_STORAGE)
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                            throw new RuntimeException("no permission");
                        }
                    }
                })
                .compose(mRxDownload.<Boolean>transformService(url))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        ArmsUtils.makeText(getActivity(), "下载开始");
                    }
                });
    }

    //暂停下载
    private void pause() {
        mRxDownload.pauseServiceDownload(url).subscribe();
    }

    //删除下载文件
    private void delete() {
        //暂停地址为url的下载并从数据库中删除记录，deleteFile为true会同时删除该url下载产生的所有文件
        mRxDownload.deleteServiceDownload(url, true).subscribe();
    }

    //获取文件路径方法
    //利用url获取
//    File[] files = rxDownload.getRealFiles(url);
//    if (files != null) {
//        File file = files[0];
//    }
//    //利用saveName及savePath获取
//    File file = rxDownload.getRealFiles(saveName,savePath)[0];

    @OnClick({R.id.iv_close, R.id.btn_ok, R.id.tv_ignore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok: //开始下载
                start();
                break;
            case R.id.tv_ignore: //忽略此版本
                ArmsUtils.makeText(view.getContext(), "忽略此版本");
                dismiss();
                break;
            case R.id.iv_close: //关闭更新窗口
                dismiss();
                break;
        }
    }

    private void installApk() {
        File[] files = mRxDownload.getRealFiles(url);
        if (files != null) {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationInfo().packageName + ".provider", files[0]);
            } else {
                uri = Uri.fromFile(files[0]);
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(intent);
        } else {
            ArmsUtils.makeText(getActivity(), "File not exists");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
