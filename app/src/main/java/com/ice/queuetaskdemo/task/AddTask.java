package com.ice.queuetaskdemo.task;

import android.util.Log;

import com.ice.queuetaskdemo.manager.IStorage;
import com.ice.queuetaskdemo.manager.UploadQueueManager;
import com.ice.queuetaskdemo.util.Util;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends BaseTask{

    public AddTask(IStorage storage, UploadQueueManager uploadQueueManager) {
        super(storage, uploadQueueManager);
    }

    @Override
    public void run() {
        Log.d(Util.TAG,"currentThreadId:"+Thread.currentThread().getId());
        List<UploadTaskInfo> allUploadTaskList = getAllUploadTaskList();
        ArrayList<UploadTaskInfo> realTaskList = new ArrayList<>();
        for(UploadTaskInfo uploadTaskInfo:allUploadTaskList){
            if(!getUploadQueueManager().containsTask(uploadTaskInfo)){
                realTaskList.add(uploadTaskInfo);
            }
        }
        getUploadQueueManager().enqueueTasks(realTaskList);
    }
}
