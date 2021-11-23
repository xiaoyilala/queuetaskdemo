package com.ice.queuetaskdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbManager {
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    public static SQLiteDatabase db;

    public static void setUpDatabase(Context context) {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        //该实例化会报错，但是不影响功能使用；是加密问题；见：https://blog.csdn.net/lidiwo/article/details/78614531
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                "ice_db.db", null);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static void clean() {
        daoSession.clear();
        daoMaster = null;
        daoSession = null;
    }

    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            synchronized (DbManager.class) {
                if (daoMaster == null) {
                    daoMaster = new DaoMaster(db);
                }
                daoSession = daoMaster.newSession();
            }
        }
        return daoSession;
    }
}
