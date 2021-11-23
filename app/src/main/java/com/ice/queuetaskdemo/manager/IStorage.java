package com.ice.queuetaskdemo.manager;

import com.ice.queuetaskdemo.db.CellModel;
import com.ice.queuetaskdemo.db.OrgModel;
import com.ice.queuetaskdemo.task.UploadTaskInfo;

import java.util.List;

public interface IStorage {
    /*** 保存到数据库 */
    void saveData(OrgModel orgModel, List<CellModel> cellModelList);
    /*** 保存到数据库 */
    void saveDataBack(OrgModel orgModel, List<CellModel> cellModelList);

    /*** 更新所有数据 */
    void updateOrgModelList(List<OrgModel> orgModelList);

    /*** 删除所有数据 */
    void removeOrgModelList(List<OrgModel> orgModelList);

    /*** 获取所有初始状态的数据 */
    List<OrgModel> queryAllInitOrgModelList();

    /*** 获取所有数据 */
    List<OrgModel> queryAllOrgModelList();

    /*** 重置异常数据 */
    void resetErrorOrgModelList();

    /*** 获取所有打包好的数据task */
    List<UploadTaskInfo> queryAllUploadTask();
}
