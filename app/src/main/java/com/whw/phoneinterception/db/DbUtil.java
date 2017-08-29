package com.whw.phoneinterception.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wuhaiwen on 2017/8/29.
 */

public class DbUtil extends SQLiteOpenHelper {

    //版本号，在每次升级的时候都需要在这个版本号上加一
    private static int version = 1;
    private static DbUtil dbUtil;
    private static String db_name = "record.db";

    /**
     * 获得数据库的实例
     * @param context
     * @return
     */
    public  static DbUtil getInstance(Context context){
        if(dbUtil == null){
            dbUtil = new DbUtil(context);
        }
        return dbUtil;
    }

    /**
     * 初始化数据库
     * @param context
     */
    public DbUtil(Context context) {
        super(context, db_name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
