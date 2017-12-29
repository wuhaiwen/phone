package com.whw.phoneinterception.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.whw.phoneinterception.util.TelephonyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wuhaiwen on 2017/8/28.
 */

public class PhoneReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.intent.action.PHONE_STATE";
    private static final String TAG="wuhaiwen";
    @Override
    public void onReceive(Context context, Intent intent) {
        doReceivePhone(context,intent);
    }

    public void doReceivePhone(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(
                TelephonyManager.EXTRA_INCOMING_NUMBER);
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        int state = telephony.getCallState();
        Log.i(TAG, "[Broadcast]=" + intent.getAction());
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.i(TAG, "[Broadcast]等待接电话=" + phoneNumber);
                try {
                    ITelephony iTelephony = TelephonyUtils.getITelephony(telephony);
                    iTelephony.endCall();
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM月dd号 HH:mm");
                    String dateString = simpleDateFormat1.format(date);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i(TAG, "挂断=" + phoneNumber);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i(TAG, "通话中=" + phoneNumber);
                break;
        }
    }
}
