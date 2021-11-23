package com.ice.queuetaskdemo.task;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ice.queuetaskdemo.db.OrgModel;
import com.ice.queuetaskdemo.util.MD5Utils;
import com.ice.queuetaskdemo.util.Util;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UploadTaskInfo {
    private String id;
    private List<OrgModel> orgModelList;

    public UploadTaskInfo(List<OrgModel> orgModelList) {
        this.id = generateId(orgModelList);
        this.orgModelList = orgModelList;
    }

    private String generateId(List<OrgModel> collectList){
        StringBuilder sb = new StringBuilder();
        sb.append(UUID.randomUUID());
        return MD5Utils.md5String(sb.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadTaskInfo)) {
            return false;
        }
        UploadTaskInfo that = (UploadTaskInfo) o;
        return Objects.equals(id, that.id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrgModel> getOrgModelList() {
        return orgModelList;
    }

    public void setOrgModelList(List<OrgModel> orgModelList) {
        this.orgModelList = orgModelList;
    }
}
