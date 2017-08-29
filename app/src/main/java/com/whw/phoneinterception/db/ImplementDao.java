package com.whw.phoneinterception.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wuhaiwen on 2017/8/29.
 */

public class ImplementDao implements RecordDao {
    private DbUtil dbUtil;
    private SQLiteDatabase database;

    public ImplementDao(Context context) {
        dbUtil = DbUtil.getInstance(context);
        database = dbUtil.getWritableDatabase();
    }
}
