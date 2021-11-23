package com.ice.queuetaskdemo.manager;

import android.content.Context;

import com.ice.queuetaskdemo.config.Defaults;
import com.ice.queuetaskdemo.config.UploaderConfig;
import com.ice.queuetaskdemo.db.CellModel;
import com.ice.queuetaskdemo.db.OrgModel;

import java.util.List;

public class UploadManger {

    private UploadImpl uploadImpl;
    private static volatile UploadManger instance = null;
    private UploadManger(Context context){
        UploaderConfig.Builder builder = new UploaderConfig.Builder();
        UploaderConfig uploaderConfig = new UploaderConfig(builder);
        uploadImpl = UploadImpl.getInstance(context, uploaderConfig);
        uploadImpl.init();
    }
    public static UploadManger getInstance(Context context){
        if(instance == null){
            synchronized (UploadManger.class){
                if(instance == null){
                    instance = new UploadManger(context);
                }
            }
        }
        return instance;
    }

    public void saveData(OrgModel orgModel, List<CellModel> cellModelList){
        StorageImpl.getInstance().saveData(orgModel, cellModelList);
    }

    public void resetErrorData(){
        StorageImpl.getInstance().resetErrorOrgModelList();
    }

    /**
     * 定时循环执行任务
     * */
    public void startUploadCircle(){
        uploadImpl.startUpload();
        Defaults.HANDLER_UI.postDelayed(()->{
            startUploadCircle();
        },60000*3);
    }
}
