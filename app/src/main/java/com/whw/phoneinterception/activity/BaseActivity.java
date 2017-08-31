package com.whw.phoneinterception.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.whw.phoneinterception.Constant;
import com.whw.phoneinterception.R;
import com.whw.phoneinterception.myinterface.RequestPermissionType;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    public void printLog(String msg) {
        Log.d(Constant.TAG, msg);
    }

    public void startActivityAnimation() {
        //转场动画,启动活动 右进左出
        overridePendingTransition(
                R.anim.right_in,
                R.anim.left_out
        );
    }

    public void finishActivityAnimation() {
        //结束活动，坐进右出
        overridePendingTransition(
                R.anim.left_in,
                R.anim.right_out
        );
    }

    public void upToDownAnimation(View view, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_top_bottom);
        animation.setRepeatCount(1);
        view.startAnimation(animation);
        animation.setDuration(200);
        animation.start();
    }

    public void gone(View view, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up_none);
        animation.setRepeatCount(1);
        view.startAnimation(animation);
        animation.start();
    }

    public void loadAnimation(ImageView imageView) {
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    public boolean requestPermission(String permission, Activity activity,Context context) {
        boolean isAllow;
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission},
                        RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE);
                return false;
            } else {
                isAllow = true;
            }
        } else {
            isAllow = true;
        }
        return isAllow;
    }
}
