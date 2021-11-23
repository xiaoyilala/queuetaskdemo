package com.ice.queuetaskdemo.task;

import android.util.Log;

import com.ice.queuetaskdemo.manager.IStorage;
import com.ice.queuetaskdemo.manager.UploadQueueManager;
import com.ice.queuetaskdemo.util.Util;

public class InitTask extends BaseTask{

    public InitTask(IStorage storage, UploadQueueManager uploadQueueManager) {
        super(storage, uploadQueueManager);
    }

    @Override
    public void run() {
        Log.d(Util.TAG,"currentThreadId:"+Thread.currentThread().getId());
        getUploadQueueManager().init(getAllUploadTaskList());
    }
}
