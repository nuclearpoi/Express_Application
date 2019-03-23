package com.bs.express.base;

import android.app.Application;
import android.content.Context;
import cn.bmob.v3.Bmob;


public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Bmob.initialize(this, "cf28ff0ed6114fa61516769781a3a1f0");
    }


    public static Context getContext(){
        return mContext;
    }



}
