package com.ice.queuetaskdemo.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class OrgModel {
    /*** 唯一标识 */
    @Id
    @Unique
    private String id;

    /*** 上送状态 */
    private int uploadState;

    @ToMany(referencedJoinProperty = "orgModelId")
    private List<CellModel> cellModelList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1245507848)
    private transient OrgModelDao myDao;


    @Generated(hash = 543033258)
    public OrgModel(String id, int uploadState) {
        this.id = id;
        this.uploadState = uploadState;
    }

    @Generated(hash = 61986805)
    public OrgModel() {
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUploadState() {
        return this.uploadState;
    }

    public void setUploadState(int uploadState) {
        this.uploadState = uploadState;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1725480079)
    public List<CellModel> getCellModelList() {
        if (cellModelList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CellModelDao targetDao = daoSession.getCellModelDao();
            List<CellModel> cellModelListNew = targetDao
                    ._queryOrgModel_CellModelList(id);
            synchronized (this) {
                if (cellModelList == null) {
                    cellModelList = cellModelListNew;
                }
            }
        }
        return cellModelList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1952397487)
    public synchronized void resetCellModelList() {
        cellModelList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 374808138)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrgModelDao() : null;
    }


}
