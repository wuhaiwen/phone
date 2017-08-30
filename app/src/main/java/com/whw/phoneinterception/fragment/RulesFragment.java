package com.whw.phoneinterception.fragment;


import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.whw.phoneinterception.R;
import com.whw.phoneinterception.activity.AddWhiteListActivity;
import com.whw.phoneinterception.myinterface.RequestPermissionType;
import com.whw.phoneinterception.service.ContactsService;
import com.whw.phoneinterception.util.DialogUtil;
import com.whw.phoneinterception.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RulesFragment extends Fragment {


    Context context;
    @Bind(R.id.button)
    Button button;

    public RulesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rules, container, false);
        ButterKnife.bind(this, view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddWhiteListActivity.class);
//                startActivity(intent);
                requestPermission();
//                queryContacts();

            }
        });
        return view;
    }

    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS},
                        RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                queryContacts();
            }
        } else {
            queryContacts();
        }
    }

    public void queryContacts() {
        Intent intent = new Intent(getActivity(), ContactsService.class);
        context.startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < grantResults.length; i++) {

//判断权限的结果，如果有被拒绝，就return

            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                ToastUtil.show(context,"reject");
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),

                        permissions[i])) {


                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);

                    intent.setData(uri);
                    ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Service.ACTIVITY_SERVICE);
                    startActivityForResult(intent, 0);
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show(context, "yes");
                        queryContacts();
                    } else
                        ToastUtil.show(context, "no");
                }
            }
        }
    }
}