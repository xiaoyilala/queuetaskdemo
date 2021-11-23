package com.ice.queuetaskdemo.manager;

import android.util.Log;

import com.ice.queuetaskdemo.App;
import com.ice.queuetaskdemo.config.Defaults;
import com.ice.queuetaskdemo.db.CellModel;
import com.ice.queuetaskdemo.db.CellModelDao;
import com.ice.queuetaskdemo.db.DaoSession;
import com.ice.queuetaskdemo.db.DbManager;
import com.ice.queuetaskdemo.db.OrgModel;
import com.ice.queuetaskdemo.db.OrgModelDao;
import com.ice.queuetaskdemo.db.UploadStatus;
import com.ice.queuetaskdemo.task.SaveTask;
import com.ice.queuetaskdemo.task.UploadTaskInfo;
import com.ice.queuetaskdemo.util.Util;

import java.util.ArrayList;
import java.util.List;

public class StorageImpl implements IStorage{

    private static StorageImpl iceStorage;
    private DaoSession daoSession;
    private CellModelDao cellModelDao;
    private OrgModelDao orgModelDao;

    private StorageImpl() {
        daoSession = DbManager.getDaoSession();
        cellModelDao = daoSession.getCellModelDao();
        orgModelDao = daoSession.getOrgModelDao();
    }

    public static StorageImpl getInstance(){
        if(iceStorage == null){
            synchronized (StorageImpl.class){
                if(iceStorage == null){
                    iceStorage = new StorageImpl();
                }
            }
        }
        return iceStorage;
    }

    @Override
    public synchronized void saveData(OrgModel orgModel, final List<CellModel> cellModelList) {
        if(orgModel == null || Util.isEmpty(cellModelList)){
            return;
        }
        ArrayList<CellModel> cellModels = new ArrayList<>();
        cellModels.addAll(cellModelList);
        Defaults.getLoadingHandler().post(new SaveTask(orgModel, cellModels));
    }

    @Override
    public synchronized void saveDataBack(OrgModel orgModel, List<CellModel> cellModelList) {
        if(orgModel == null || Util.isEmpty(cellModelList)){
            return;
        }
        daoSession.runInTx(()->{
            orgModelDao.insertInTx(orgModel);
            ArrayList<CellModel> cellModels = new ArrayList<>();
            for(CellModel cellModel :cellModelList){
                cellModel.setOrgModelId(orgModel.getId());
                cellModels.add(cellModel);
            }
            cellModelDao.insertInTx(cellModels);
        });
    }

    @Override
    public synchronized void updateOrgModelList(List<OrgModel> orgModelList) {
        if(Util.isEmpty(orgModelList)){
            return;
        }
        orgModelDao.updateInTx(orgModelList);
    }

    @Override
    public synchronized void removeOrgModelList(List<OrgModel> orgModelList) {
        if(Util.isEmpty(orgModelList)){
            return;
        }
        daoSession.runInTx(()->{
            for(OrgModel collect:orgModelList){
                List<CellModel> modelList = collect.getCellModelList();
                cellModelDao.deleteInTx(modelList);
            }
            orgModelDao.deleteInTx(orgModelList);
        });
    }

    @Override
    public synchronized List<OrgModel> queryAllInitOrgModelList() {
        return orgModelDao.queryBuilder()
                .where(OrgModelDao.Properties.UploadState.eq(UploadStatus.INIT.getStatus()))
                .list();
    }

    @Override
    public synchronized List<OrgModel> queryAllOrgModelList() {
        return orgModelDao.queryBuilder().list();
    }

    @Override
    public synchronized void resetErrorOrgModelList() {
        Defaults.getLoadingHandler().post(()->{
            reset();
            Defaults.HANDLER_UI.post(()->{
                UploadManger.getInstance(App.context).startUploadCircle();
            });
        });
    }

    private void reset(){
        List<OrgModel> orgModels = orgModelDao.queryBuilder()
                .where(OrgModelDao.Properties.UploadState.notEq(UploadStatus.INIT.getStatus()))
                .list();
        ArrayList<OrgModel> collects = new ArrayList<>();
        for(OrgModel orgModel : orgModels){
            orgModel.setUploadState(UploadStatus.INIT.getStatus());
            collects.add(orgModel);
        }
        updateOrgModelList(collects);
    }

    public static final int POS_COUNT = 10;
    /**获取所有需要处理的任务  10个一组*/
    @Override
    public List<UploadTaskInfo> queryAllUploadTask() {
        List<UploadTaskInfo> taskInfoList = new ArrayList<>();
        List<OrgModel> orgModelList = queryAllInitOrgModelList();
        if(!Util.isEmpty(orgModelList)){
            int count = orgModelList.size() / POS_COUNT;
            if(orgModelList.size() % POS_COUNT != 0){
                count++;
            }
            Log.d(Util.TAG, "一共有"+ orgModelList.size()+"条数据，"+"分"+count+"批处理");
            for(int i=0; i<count; i++){
                List<OrgModel> collectPack = new ArrayList<>();
                if(i == count -1){
                    for(int j = i*POS_COUNT; j<= orgModelList.size()-1; j++){
                        collectPack.add(orgModelList.get(j));
                    }
                }else{
                    for(int j = i*POS_COUNT; j<=(i+1)*POS_COUNT -1; j++){
                        collectPack.add(orgModelList.get(j));
                    }
                }
                taskInfoList.add(new UploadTaskInfo(collectPack));
            }
        }else{
            Log.d(Util.TAG, "没有数据需要处理");
        }
        return taskInfoList;
    }
}
