package com.ice.queuetaskdemo.config;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Defaults {
    public static final int MAX_UPLOAD_COUNT = 5;
    private static Handler HANDLER_LOADING;
    private static HandlerThread THREAD_LOADING;
    public static Handler HANDLER_UI = new Handler(Looper.getMainLooper());
    public static ExecutorService THREAD_POOL_UPLOAD = Executors.newFixedThreadPool(3);

    public synchronized static Handler getLoadingHandler(){
        if(null == HANDLER_LOADING){
            THREAD_LOADING = new HandlerThread("iceloading");
            THREAD_LOADING.start();
            HANDLER_LOADING = new Handler(THREAD_LOADING.getLooper());
        }
        return HANDLER_LOADING;
    }
}
