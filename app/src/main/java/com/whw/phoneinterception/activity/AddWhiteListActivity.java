package com.whw.phoneinterception.activity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
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

import com.whw.phoneinterception.R;
import com.whw.phoneinterception.util.ToastUtil;
import com.whw.phoneinterception.widget.LoadingView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddWhiteListActivity extends BaseActivity {
    @Bind(R.id.loading)
    ImageView imageView;
    LoadingView loadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_white_list);
        ButterKnife.bind(this);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < grantResults.length; i++) {

//判断权限的结果，如果有被拒绝，就return

            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                ToastUtil.show(this,"reject");
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,

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
