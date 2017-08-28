package com.whw.phoneinterception.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import com.whw.phoneinterception.Constant;

/**
 * Created by wuhaiwen on 2017/8/28.
 */

public class SharePreferencesUtils {

    public static void setValue(Context context,String key,String value){
        SharedPreferences.Editor editor  = context.getSharedPreferences(Constant.USER_SETTINGS,Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static String getValue(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(Constant.USER_SETTINGS,Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }

}
