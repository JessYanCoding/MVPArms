package me.qing.eye.pubblico.util;

import android.os.Environment;

import java.io.File;

public class FileUtil {

    public final static String FILE_PATH = "file_path";
    public final static String FILE_NAME = "file_name";

    /**
     * SdCard 相关
     */

    /**
     * SdCard的根目录
     */
    public static String getSdCardPath() {
        if (isAvailable()) {
            return Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        }
        return null;
    }

    public static boolean isAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    public static void mkdir(String dir) {
        File file = new File(dir);
        // 创建文件夹
        file.mkdirs();
    }

    public static boolean isEmpty(String dir) {
        File file = new File(dir);
        if (file.isDirectory()) {
            String files[] = file.list();
            if (files.length > 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        if (tempList.length == 0) {
            return true;
        }
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
                flag = true;
            }

        }
        return flag;
    }


}

