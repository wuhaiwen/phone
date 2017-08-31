package com.whw.phoneinterception.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import  android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

import com.whw.phoneinterception.Constant;
import com.whw.phoneinterception.bean.Contacts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * 获取联系人的服务
 * Created by wuhaiwen on 2017/8/30.
 */

public class ContactsService extends Service {

    private ArrayList<Contacts> contactsList = new ArrayList<>();
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
            //需要获取的内容
            String[] projection = {
                    Phone._ID,
                    Phone.NUMBER,
                    Phone.DISPLAY_NAME,
                    Phone.PHOTO_URI,
                    Phone.PHOTO_ID
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
                String photo_uri = cursor.getString(3);
                long photo_id = cursor.getLong(4);
                Contacts contacts = new Contacts(id,contacts_name,number,photo_uri,photo_id);
                contactsList.add(contacts);
                Log.d(Constant.TAG,contacts_name+" "+photo_id+" "+id);
            }
            cursor.close();
            TreeSet<Long> treeSet = new TreeSet<>();
            for (Contacts contacts:contactsList
                 ) {
                treeSet.add(contacts.getId());
            }
            for (Iterator iterator = treeSet.iterator(); iterator.hasNext();) {
                long id = (long) iterator.next();
            }
            Intent intent = new Intent(Constant.CONTACTS_LIST);
            intent.putExtra(Constant.CONTACTS_LIST,contactsList);
            //获取联系人列表，发送广播
            sendBroadcast(intent);
        }
    }
}
