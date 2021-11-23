package com.ice.queuetaskdemo.util;

import android.os.Looper;

import java.util.List;

public class Util {

    public static final String TAG = "queuetaskdemo_tag";

    public static <T> boolean isEmpty(List<T> list){
        if(list == null || list.isEmpty()){
            return true;
        }
        return false;
    }

    public static void checkState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void checkUIThread(String method) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return;
        }
        throw new RuntimeException("方法[" + method + "]必须在主线程中执行");
    }
}
