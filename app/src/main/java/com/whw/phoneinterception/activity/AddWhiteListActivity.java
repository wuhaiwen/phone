package com.whw.phoneinterception.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.whw.phoneinterception.Constant;
import com.whw.phoneinterception.R;
import com.whw.phoneinterception.adapter.ContactsAdapter;
import com.whw.phoneinterception.bean.Contacts;
import com.whw.phoneinterception.service.ContactsService;
import com.whw.phoneinterception.util.ToastUtil;
import com.whw.phoneinterception.widget.LoadingView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddWhiteListActivity extends BaseActivity {
    @Bind(R.id.loading)
    ImageView imageView;
//    @Bind(R.id.loading_view)
//    LoadingView loadingView;

    ArrayList<Contacts> list;
    Context context;
    Intent intent_service;

    @Bind(R.id.contacts_list)
    ListView contact_list;

    private ContactsAdapter contactsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_white_list);
        ButterKnife.bind(this);
        context = this;
        loadAnimation(imageView);
        if (requestPermission(Manifest.permission.READ_CONTACTS, this, getApplicationContext())) {
            intent_service = new Intent(this, ContactsService.class);
            this.startService(intent_service);
            IntentFilter filter = new IntentFilter(Constant.CONTACTS_LIST);
            registerReceiver(contactsReceiver, filter);
        }
    }

    /**
     * 接受联系人的广播
     */
    BroadcastReceiver contactsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            list = (ArrayList<Contacts>) intent.getSerializableExtra(Constant.CONTACTS_LIST);
            if (list != null) {
                if (list.size() > 0) {
                    contactsAdapter = new ContactsAdapter(list,context);
                    contact_list.setAdapter(contactsAdapter);
                } else {
                    showToast("未找到联系人");
                }
                imageView.setVisibility(View.GONE);
            }
            context.stopService(intent_service);
            context.unregisterReceiver(contactsReceiver);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

//        unregisterReceiver(contactsReceiver);
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        for (int i = 0; i < grantResults.length; i++) {
//
////判断权限的结果，如果有被拒绝，就return
//
//            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                ToastUtil.show(context,"reject");
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//
//                        permissions[i])) {
//
//
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//
//                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
//
//                    intent.setData(uri);
//                    ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Service.ACTIVITY_SERVICE);
//                    startActivityForResult(intent, 0);
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        ToastUtil.show(context, "yes");
//                        queryContacts();
//                    } else
//                        ToastUtil.show(context, "no");
//                }
//            }
//        }
//    }
}
