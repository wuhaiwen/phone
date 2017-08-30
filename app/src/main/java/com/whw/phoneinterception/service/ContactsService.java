package com.whw.phoneinterception.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import  android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

import com.whw.phoneinterception.Constant;

/**
 * Created by wuhaiwen on 2017/8/30.
 */

public class ContactsService extends Service {

    public ContactsService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        new ContactsThread().start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class ContactsThread extends Thread{
        @Override
        public void run() {
            super.run();
            ContentResolver resolver = getContentResolver();
            Uri uri = Phone.CONTENT_URI;
            String[] projection = {
                    Phone._ID,
                    Phone.NUMBER,
                    Phone.DISPLAY_NAME
            };
            Cursor cursor = resolver.query(
                    uri,
                    projection,
                    null,
                    null,
                    null
            );
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String number = cursor.getString(1);
                String contacts_name = cursor.getString(2);
                Log.d(Constant.TAG,contacts_name);
            }
            cursor.close();
        }
    }
}
