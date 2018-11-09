package com.xinluhuang.englishlearn.application;

import android.app.Application;
import android.content.Context;

import com.xinluhuang.englishlearn.greendao.DAOManager;
import com.xinluhuang.englishlearn.util.SPUtil;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DAOManager.getInstance().init(getContext());
        SPUtil.init(getContext());
    }

    public static Context getContext() {
        return context;
    }

}
