package com.jess.arms.common.time;

import android.os.Handler;
import android.os.Message;

/**
 * 自定义的倒计时类，没有用官方提供的CountDownTimer来实现.有暂停等方法，灵活性强.
 * 在activity退出后还会持续计时，所以结束时需要判断当前activity是否在前台<br>
 *
 * @author lujianzhao
 * @date 2015/4/24
 * @see "http://www.cnblogs.com/tianzhijiexian/p/4459216.html"
 */

public abstract class AdvancedCountdownTimer {

    private final long mCountdownInterval;

    private long mTotalTime;

    private long mRemainTime;

    /**
     * @param millisInFuture    表示以毫秒为单位 倒计时的总数
     *
     *                          例如 millisInFuture=1000 表示1秒
     * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
     *
     *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
     */
    public AdvancedCountdownTimer(long millisInFuture, long countDownInterval) {
        mTotalTime = millisInFuture;
        mCountdownInterval = countDownInterval;
        mRemainTime = millisInFuture;
    }

    public final void seek(int value) {
        synchronized (AdvancedCountdownTimer.this) {
            mRemainTime = ((100 - value) * mTotalTime) / 100;
        }
    }

    public final void cancel() {
        mHandler.removeMessages(MSG_RUN);
        mHandler.removeMessages(MSG_PAUSE);
    }

    public final void resume() {
        mHandler.removeMessages(MSG_PAUSE);
        mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_RUN));
    }

    public final void pause() {
        mHandler.removeMessages(MSG_RUN);
        mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_PAUSE));
    }

    public synchronized final AdvancedCountdownTimer start() {
        if (mRemainTime <= 0) {
            onFinish();
            return this;
        }
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_RUN), mCountdownInterval);
        return this;
    }

    public abstract void onTick(long millisUntilFinished, int percent);

    public abstract void onFinish();

    private static final int MSG_RUN = 1;

    private static final int MSG_PAUSE = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (AdvancedCountdownTimer.this) {
                if (msg.what == MSG_RUN) {
                    mRemainTime = mRemainTime - mCountdownInterval;
                    if (mRemainTime <= 0) {
                        onFinish();
                    } else if (mRemainTime < mCountdownInterval) {
                        sendMessageDelayed(obtainMessage(MSG_RUN), mRemainTime);
                    } else {
                        onTick(mRemainTime, Long.valueOf(100 * (mTotalTime - mRemainTime) / mTotalTime).intValue());

                        sendMessageDelayed(obtainMessage(MSG_RUN), mCountdownInterval);
                    }
                } else if (msg.what == MSG_PAUSE) {
                }
            }
        }
    };

}  