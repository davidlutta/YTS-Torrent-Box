package com.davidlutta.ytsapp;

import android.app.Application;
import android.content.Context;

public class GlobalApplication extends Application {
    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
}
