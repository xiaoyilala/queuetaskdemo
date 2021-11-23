package com.ice.queuetaskdemo.task;

import android.text.TextUtils;

import com.ice.queuetaskdemo.manager.IStorage;
import com.ice.queuetaskdemo.manager.UploadQueueManager;
import com.ice.queuetaskdemo.util.Util;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTask implements Runnable {
    private IStorage storage;
    private UploadQueueManager uploadQueueManager;

    public BaseTask(IStorage storage, UploadQueueManager uploadQueueManager) {
        this.storage = storage;
        this.uploadQueueManager = uploadQueueManager;
    }

    public List<UploadTaskInfo> getAllUploadTaskList(){
        List<UploadTaskInfo> taskInfoList = storage.queryAllUploadTask();
        List<UploadTaskInfo> realTasks = new ArrayList<>();
        if(!Util.isEmpty(taskInfoList)){
            for(UploadTaskInfo taskInfo:taskInfoList){
                if(!TextUtils.isEmpty(taskInfo.getId()) && !Util.isEmpty(taskInfo.getOrgModelList())){
                    realTasks.add(taskInfo);
                }
            }
        }
        return realTasks;
    }

    public IStorage getStorage() {
        return storage;
    }

    public void setStorage(IStorage storage) {
        this.storage = storage;
    }

    public UploadQueueManager getUploadQueueManager() {
        return uploadQueueManager;
    }

    public void setUploadQueueManager(UploadQueueManager uploadQueueManager) {
        this.uploadQueueManager = uploadQueueManager;
    }
}
