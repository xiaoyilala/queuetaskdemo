package com.ice.queuetaskdemo;

import android.app.Application;
import android.content.Context;

import com.ice.queuetaskdemo.db.DbManager;
import com.ice.queuetaskdemo.manager.UploadManger;

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        DbManager.setUpDatabase(context);
        UploadManger.getInstance(context).resetErrorData();
    }
}
