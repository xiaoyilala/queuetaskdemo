package com.ice.queuetaskdemo.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity
public class CellModel {

    /*** 唯一标识 */
    @Id(autoincrement = true)
    private Long id;

    private String name;
    private int age;
    private String orgModelId;

    @ToOne(joinProperty = "orgModelId")
    private OrgModel orgModel;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1519802888)
    private transient CellModelDao myDao;

    @Generated(hash = 1822013706)
    public CellModel(Long id, String name, int age, String orgModelId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.orgModelId = orgModelId;
    }
    @Generated(hash = 2111446025)
    public CellModel() {
    }
    @Generated(hash = 1994925303)
    private transient String orgModel__resolvedKey;

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getOrgModelId() {
        return this.orgModelId;
    }
    public void setOrgModelId(String orgModelId) {
        this.orgModelId = orgModelId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1596943566)
    public OrgModel getOrgModel() {
        String __key = this.orgModelId;
        if (orgModel__resolvedKey == null || orgModel__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OrgModelDao targetDao = daoSession.getOrgModelDao();
            OrgModel orgModelNew = targetDao.load(__key);
            synchronized (this) {
                orgModel = orgModelNew;
                orgModel__resolvedKey = __key;
            }
        }
        return orgModel;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1672495967)
    public void setOrgModel(OrgModel orgModel) {
        synchronized (this) {
            this.orgModel = orgModel;
            orgModelId = orgModel == null ? null : orgModel.getId();
            orgModel__resolvedKey = orgModelId;
        }
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
    @Generated(hash = 1639341664)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCellModelDao() : null;
    }

}
