package com.whw.phoneinterception.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.provider.ContactsContract.CommonDataKinds.Phone;
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

    class ContactsThread extends Thread {
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
                Contacts contacts = new Contacts(id, contacts_name, number, photo_uri, photo_id);
                contactsList.add(contacts);
//                Log.d(Constant.TAG, contacts_name + " " + photo_id + " " + id + " " + number);
            }
            cursor.close();
            Intent intent = new Intent(Constant.CONTACTS_LIST);
            intent.putExtra(Constant.CONTACTS_LIST, contactsList);
            //获取联系人列表，发送广播
            sendBroadcast(intent);
            printContacts();
        }
    }

    public void printContacts() {
        //生成ContentResolver对象
        ContentResolver contentResolver = getContentResolver();

        // 获得所有的联系人
        /*Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
         */
        //这段代码和上面代码是等价的，使用两种方式获得联系人的Uri
        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/contacts"), null, null, null, null);

        // 循环遍历
        if (cursor.moveToFirst()) {

            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            while (cursor.moveToNext()) {
                // 获得联系人的ID
                String contactId = cursor.getString(idColumn);
                // 获得联系人姓名
                String displayName = cursor.getString(displayNameColumn);

                // 查看联系人有多少个号码，如果没有号码，返回0
                int phoneCount = cursor
                        .getInt(cursor
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    // 获得联系人的电话号码列表
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + "=" + contactId, null, null);
                    if (phoneCursor.moveToFirst()) {
                        while (phoneCursor.moveToNext()){
                            //遍历所有的联系人下面所有的电话号码
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Log.d(Constant.TAG, displayName + " " + phoneNumber);
                        }
                    }
                    phoneCursor.close();
                }

            }
            cursor.close();
        }

    }
}
