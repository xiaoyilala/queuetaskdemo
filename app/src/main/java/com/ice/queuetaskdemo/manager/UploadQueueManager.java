package com.ice.queuetaskdemo.manager;

import com.ice.queuetaskdemo.config.UploaderConfig;
import com.ice.queuetaskdemo.db.OrgModel;
import com.ice.queuetaskdemo.db.UploadStatus;
import com.ice.queuetaskdemo.task.RealUploadTask;
import com.ice.queuetaskdemo.task.UploadTaskInfo;
import com.ice.queuetaskdemo.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UploadQueueManager {

    private UploaderConfig config;
    private final List<UploadTaskInfo> runningQueue = new LinkedList<>();
    private final List<UploadTaskInfo> waitingQueue = new LinkedList<>();
    private final Map<UploadTaskInfo, RealUploadTask> taskMap = new HashMap<>();

    public UploadQueueManager(UploaderConfig config) {
        this.config = config;
    }

    public void init(List<UploadTaskInfo> taskInfoList){
        if(Util.isEmpty(taskInfoList)){
            return;
        }
        synchronized (this){
            Util.checkState(runningQueue.isEmpty() && waitingQueue.isEmpty(), "init方法必须最先调用，而且只能调用1次");
            for(int i=0; i<taskInfoList.size(); i++){
                if(i<config.mMaxUploadingCount){
                    runningQueue.add(taskInfoList.get(i));
                    updateStatus(taskInfoList.get(i).getOrgModelList(), UploadStatus.UPLOADING);
                }else{
                    waitingQueue.add(taskInfoList.get(i));
                    updateStatus(taskInfoList.get(i).getOrgModelList(), UploadStatus.WAITING);
                }
            }
            for(UploadTaskInfo uploadTaskInfo:runningQueue){
                uploadLock(uploadTaskInfo);
            }
        }
    }

    private void updateStatus(List<OrgModel> orgModelList, UploadStatus status){
        ArrayList<OrgModel> list = new ArrayList<>();
        for(OrgModel orgModel : orgModelList){
            orgModel.setUploadState(status.getStatus());
            list.add(orgModel);
        }
        StorageImpl.getInstance().updateOrgModelList(list);
    }

    private void uploadLock(UploadTaskInfo uploadTaskInfo){
        RealUploadTask realUploadTask = new RealUploadTask(StorageImpl.getInstance(), this, uploadTaskInfo);
        taskMap.put(uploadTaskInfo, realUploadTask);
        config.mUploaderService.execute(realUploadTask);
    }

    public boolean containsTask(UploadTaskInfo taskInfo) {
        synchronized (this) {
            return (runningQueue.contains(taskInfo) || waitingQueue.contains(taskInfo));
        }
    }

    public void enqueue(UploadTaskInfo uploadTaskInfo){
        if(uploadTaskInfo==null){
            return;
        }
        synchronized (this){
            if(runningQueue.size()>=config.mMaxUploadingCount){
                waitingQueue.add(uploadTaskInfo);
                updateStatus(uploadTaskInfo.getOrgModelList(), UploadStatus.WAITING);
            }else{
                runningQueue.add(uploadTaskInfo);
                updateStatus(uploadTaskInfo.getOrgModelList(), UploadStatus.UPLOADING);
                uploadLock(uploadTaskInfo);
            }
        }
    }

    public void enqueueTasks(List<UploadTaskInfo> uploadTaskInfos){
        if(Util.isEmpty(uploadTaskInfos)){
            return;
        }
        for(UploadTaskInfo uploadTaskInfo:uploadTaskInfos){
            enqueue(uploadTaskInfo);
        }
    }

    /**RealUploadTask 执行完成回调*/
    public void doUploadComplete(UploadTaskInfo uploadTaskInfo){
        synchronized (this) {
            taskMap.remove(uploadTaskInfo);
            runningQueue.remove(uploadTaskInfo);
            if(waitingQueue.isEmpty()){
                return;
            }
            UploadTaskInfo remove = waitingQueue.remove(0);
            runningQueue.add(remove);
            uploadLock(remove);
        }
    }
}
