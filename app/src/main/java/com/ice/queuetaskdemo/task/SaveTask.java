package com.ice.queuetaskdemo.task;

import com.ice.queuetaskdemo.db.CellModel;
import com.ice.queuetaskdemo.db.OrgModel;
import com.ice.queuetaskdemo.manager.StorageImpl;

import java.util.List;

public class SaveTask implements Runnable{

    private OrgModel orgModel;
    private List<CellModel> cellModelList;

    public SaveTask(OrgModel orgModel, List<CellModel> cellModelList) {
        this.orgModel = orgModel;
        this.cellModelList = cellModelList;
    }

    @Override
    public void run() {
        StorageImpl.getInstance().saveDataBack(orgModel, cellModelList);
    }
}
