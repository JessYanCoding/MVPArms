package com.jess.arms.common.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.format.Formatter;

import com.apkfuns.logutils.LogUtils;


/**
 * Get memory info.
 *
 * @author lujianzhao
 * @date 2015-04-19
 */
public class MemoryUtil {
//    private static final String TAG = MemoryUtil.class.getSimpleName();
    private static final String MEM_INFO_PATH = "/proc/meminfo";

    /**
     * Print memory info. such as:
     *
     * MemTotal:        1864292 kB
     * MemFree:          779064 kB
     * Buffers:            4540 kB
     * Cached:           185656 kB
     * SwapCached:        13160 kB
     * Active:           435588 kB
     * Inactive:         269312 kB
     * Active(anon):     386188 kB
     * Inactive(anon):   132576 kB
     * Active(file):      49400 kB
     * Inactive(file):   136736 kB
     * Unevictable:        2420 kB
     * Mlocked:               0 kB
     * HighTotal:       1437692 kB
     * HighFree:         520212 kB
     * LowTotal:         426600 kB
     * LowFree:          258852 kB
     * SwapTotal:        511996 kB
     * SwapFree:         171876 kB
     * Dirty:               412 kB
     * Writeback:             0 kB
     * AnonPages:        511924 kB
     * Mapped:           152368 kB
     * Shmem:              1636 kB
     * Slab:             109224 kB
     * SReclaimable:      75932 kB
     * SUnreclaim:        33292 kB
     * KernelStack:       13056 kB
     * PageTables:        28032 kB
     * NFS_Unstable:          0 kB
     * Bounce:                0 kB
     * WritebackTmp:          0 kB
     * CommitLimit:     1444140 kB
     * Committed_AS:   25977748 kB
     * VmallocTotal:     458752 kB
     * VmallocUsed:      123448 kB
     * VmallocChunk:     205828 kB
     */
    public static String printMemInfo() {
        String info = FileUtil.getFileOutputString(MEM_INFO_PATH);
        LogUtils.i("_______  内存信息:   \n" + info);
        return info;
    }

    /**
     * Get memory info of device.
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi;
    }

    /**
     * Print Memory info.
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static ActivityManager.MemoryInfo printMemoryInfo(Context context) {
        return getMemoryInfo(context);
//        if (LogUtils.getLogConfig().isEnable()) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("_______  Memory :   ");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                sb.append("\ntotalMem        :").append(mi.totalMem);
//            }
//            sb.append("\navailMem        :").append(mi.availMem);
//            sb.append("\nlowMemory       :").append(mi.lowMemory);
//            sb.append("\nthreshold       :").append(mi.threshold);
//            LogUtils.i( sb.toString());
//        }
//        return mi;
    }

    /**
     * Get available memory info.
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static String getAvailMemory(Context context) {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }

}
