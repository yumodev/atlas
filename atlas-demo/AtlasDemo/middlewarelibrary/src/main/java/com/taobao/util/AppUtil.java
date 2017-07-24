package com.taobao.util;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by yumodev on 17/6/23.
 */

public class AppUtil {

    private static Handler mMainHandler;
    private static Context mContext;
    /**
     * 在主线程打开Toast
     */
    public static void showToast(final String message){
        getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取主线程UiHanlder
     * @return
     */
    public static Handler getMainHandler() {
        synchronized (AppUtil.class) {
            if (mMainHandler == null) {
                mMainHandler = new Handler(Looper.getMainLooper());
            }
        }
        return mMainHandler;
    }

    public static void setContext(Context context){
        mContext = context;
    }

    /**
     * 创建缓存目录
     * @return
     */
    public static boolean createCacheDir(Context context){
        String cachePath = getCachePath(context);
        File file = new File(cachePath);
        if (!file.exists()){
            file.mkdirs();
            Log.i("AppUtils", "create cacheDir:"+cachePath);
        }else{
            Log.i("AppUtils", "cacheDir has exist:"+cachePath);
        }
        return true;
    }

    /**
     * 获取app缓存路径
     * @param context
     * @return
     */
    public static String getCachePath( Context context ){
        String cachePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            cachePath = context.getExternalCacheDir().getPath() ;
        }else {
            //外部存储不可用
            cachePath = context.getCacheDir().getPath() ;
        }
        return cachePath ;
    }
}
