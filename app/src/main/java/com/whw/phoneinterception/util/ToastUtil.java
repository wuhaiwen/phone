package com.whw.phoneinterception.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 邬海文 on 2017/8/15.
 */

public class ToastUtil {

    public static void show(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
