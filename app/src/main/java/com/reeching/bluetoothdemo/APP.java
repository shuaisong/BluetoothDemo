package com.reeching.bluetoothdemo;

import android.app.Application;

/**
 * Created by lenovo on 2019/4/28.
 * auther:lenovo
 * Date：2019/4/28
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
