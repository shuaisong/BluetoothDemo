package com.reeching.bluetoothdemo;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

/**
 * Created by lenovo on 2019/4/28.
 * auther:lenovo
 * Date：2019/4/28
 */
public class APP extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        PreferenceManager.init(this);
    }
}
