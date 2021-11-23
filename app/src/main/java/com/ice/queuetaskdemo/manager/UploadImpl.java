package com.ice.queuetaskdemo.manager;

import android.content.Context;
import android.util.Log;

import com.ice.queuetaskdemo.config.UploaderConfig;
import com.ice.queuetaskdemo.task.AddTask;
import com.ice.queuetaskdemo.task.InitTask;
import com.ice.queuetaskdemo.util.Util;

public class UploadImpl implements IUpload{

    private Context context;
    private IStorage storage;
    private UploadQueueManager uploadQueueManager;
    private UploaderConfig config;

    private static volatile UploadImpl uploadImpl;
    private UploadImpl(Context context, UploaderConfig config){
        if (null == context || null == config) {
            throw new NullPointerException("null == context || null == config");
        }
        this.context = context;
        storage = StorageImpl.getInstance();
        uploadQueueManager = new UploadQueueManager(config);
        this.config = config;
    }

    public static UploadImpl getInstance(Context context, UploaderConfig config){
        if(uploadImpl==null){
            synchronized (UploadImpl.class){
                if(uploadImpl == null){
                    uploadImpl = new UploadImpl(context, config);
                }
            }
        }
        return uploadImpl;
    }

    @Override
    public void init() {
        Log.d(Util.TAG, "init 触发任务执行");
        Util.checkUIThread("init");
        InitTask initTask = new InitTask(storage, uploadQueueManager);
        config.mLoadHandler.post(initTask);
    }

    @Override
    public void startUpload() {
        Util.checkUIThread("startUpload");
        AddTask addTask = new AddTask(storage, uploadQueueManager);
        config.mLoadHandler.post(addTask);
    }
}
