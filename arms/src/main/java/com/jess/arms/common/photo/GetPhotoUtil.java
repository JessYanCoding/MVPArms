package com.jess.arms.common.photo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

/**
 * 从相册或相机得到照片并返回simplePhoto的类.
 * @author lujianzhao
 * @date 2015/4/25
 */
class GetPhotoUtil {

    /**
     * 从相册获取图片
     */
    public static void choicePicFromAlbum(Activity activity, int requestCode) {
        // 来自相册
        Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        albumIntent.addCategory(Intent.CATEGORY_OPENABLE);
        albumIntent.setType("image/*");
        activity.startActivityForResult(albumIntent, requestCode);
    }

    /**
     * 4.4以上版本使用
     * @see "http://blog.csdn.net/tempersitu/article/details/20557383"
     * 
     * @param activity
     * @param requestCode
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void choicePicFromAlbum_kitkat(Activity activity, int requestCode) {
        // 来自相册
        Intent albumIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        albumIntent.addCategory(Intent.CATEGORY_OPENABLE);
        albumIntent.setType("image/*");
        activity.startActivityForResult(albumIntent, requestCode);
    }

    /**
     * 拍照后获取图片
     *
     * @param activity           
     * @param cameraPhotoFile 照片的文件
     * @param requestCode
     */
    public static void choicePicFromCamera(Activity activity, File cameraPhotoFile,int requestCode) {
        // 来自相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径，这样通过这个uri就可以得到这个照片了
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraPhotoFile));
        activity.startActivityForResult(cameraIntent, requestCode);// CAMERA_OK是用作判断返回结果的标识
    }

    /**
     * 4.4得到的uri,需要以下方法来获取文件的路径
     * 
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider  
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider  
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes  
            }
            // DownloadsProvider  
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider  
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)  
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address  
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File  
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for 
     * MediaStore Uris, and other file-based ContentProviders. 
     *
     * @param context The context. 
     * @param uri The Uri to query. 
     * @param selection (Optional) Filter used in the query. 
     * @param selectionArgs (Optional) Selection arguments used in the query. 
     * @return The value of the _data column, which is typically a file path. 
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
            String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is ExternalStorageProvider. 
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is DownloadsProvider. 
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is MediaProvider. 
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is Google Photos. 
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 得到图片的方向
     * 
     * @param photoUri    
     * @return
     */
    public static int getOrientation(final Uri photoUri) {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(photoUri.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
    }

    /**
     * 通过photo的uri来得到图片的角度，从而判断是否需要进行旋转操作
     *
     * @param uri
     * @return
     */
    public static int getPhotoDegreeByUri(Uri uri) {
        int degree = 0;
        int orientation = GetPhotoUtil.getOrientation(uri);
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            degree = 90;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            degree = 180;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            degree = 270;
        }
        return degree;
    }

}
