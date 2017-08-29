package com.whw.phoneinterception.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whw.phoneinterception.Constant;
import com.whw.phoneinterception.R;
import com.whw.phoneinterception.adapter.RecordAdapter;
import com.whw.phoneinterception.myinterface.RequestPermissionType;
import com.whw.phoneinterception.util.SharePreferencesUtils;
import com.whw.phoneinterception.util.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {

    @Bind(R.id.recycleView)
    RecyclerView recyclerView;
    RecordAdapter recordAdapter;
    List<String> data = new ArrayList<>();

    public RecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, view);
        initView();
        SharePreferencesUtils.setValue(getActivity(), "wu", "haiwen");
        Log.d(Constant.TAG, SharePreferencesUtils.getValue(getActivity(), "wu"));
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        File path = getActivity().getExternalFilesDir("");
        File file = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File file2 = new File(file,"hah");
        if(!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    private void initView() {
        data.add("1");
        data.add("1");
        recordAdapter = new RecordAdapter(data, getActivity(),getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recordAdapter);
    }

    /**
     * 注册权限申请回调
     * @param requestCode 申请码
     * @param permissions 申请的权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case  RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //获得电话号码然后用意图启动拨号界面，不会直接拨出去
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + 10000));
                    getActivity().startActivity(intent);
                }
                else
                {
                    // Permission DeniedT
                    ToastUtil.show(getActivity(),"没有权限");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
