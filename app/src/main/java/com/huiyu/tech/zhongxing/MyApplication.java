package com.huiyu.tech.zhongxing;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by ml on 2016/8/2.
 */
public class MyApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(context);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        Stetho.initializeWithDefaults(this);
    }

}
