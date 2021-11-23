package com.ice.queuetaskdemo.task;

import android.util.Log;

import com.ice.queuetaskdemo.db.OrgModel;
import com.ice.queuetaskdemo.db.UploadStatus;
import com.ice.queuetaskdemo.manager.IStorage;
import com.ice.queuetaskdemo.manager.UploadQueueManager;
import com.ice.queuetaskdemo.util.Util;

import java.util.ArrayList;
import java.util.Random;

public class RealUploadTask extends BaseTask{

    private UploadTaskInfo uploadTaskInfo;
    private Random random;

    public RealUploadTask(IStorage storage, UploadQueueManager uploadQueueManager, UploadTaskInfo uploadTaskInfo) {
        super(storage, uploadQueueManager);
        this.uploadTaskInfo = uploadTaskInfo;
        random = new Random();
    }

    @Override
    public void run() {
        try{
            Log.d(Util.TAG, Thread.currentThread().getId() + "开始：" + uploadTaskInfo.getId() + " 数量：" + uploadTaskInfo.getOrgModelList().size());
            // todo 模拟开始执行
            Thread.sleep(5000);
            int i = random.nextInt(4);
            if(i<3){
                //模拟成功
                uploadSuccess();
            }else{
                //模拟失败
                uploadFail();
            }
        }catch (Exception e){
            uploadFail();
        }finally {
            getUploadQueueManager().doUploadComplete(uploadTaskInfo);
        }
    }

    private void uploadSuccess(){
        getStorage().removeOrgModelList(uploadTaskInfo.getOrgModelList());
    }

    private void uploadFail(){
        Log.d(Util.TAG, "任务执行失败");
        ArrayList<OrgModel> orgModels = new ArrayList<>();
        for(OrgModel orgModel:uploadTaskInfo.getOrgModelList()){
            orgModel.setUploadState(UploadStatus.INIT.getStatus());
            orgModels.add(orgModel);
        }
        getStorage().updateOrgModelList(orgModels);
    }
}
