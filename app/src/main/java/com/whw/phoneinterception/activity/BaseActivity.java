package com.whw.phoneinterception.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.whw.phoneinterception.Constant;
import com.whw.phoneinterception.R;
import com.whw.phoneinterception.myinterface.RequestPermissionType;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        work();
        startActivityAnimation();
    }


    //重写返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            finishActivityAnimation();
        }
        return super.onKeyDown(keyCode, event);
    }


    protected abstract
    @LayoutRes
    int getLayoutResId();

    protected void initToolBar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finishActivityAnimation();
            }
        });
    }

    protected void initView() {
    }

    private float mStartY = 0, mLastY = 0, mLastDeltaY;

    public void showToolbar(final ListView listView, final Toolbar toolbar, final Context context) {
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final float y = event.getY();
                float translationY = toolbar.getTranslationY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        Log.v(TAG, "Down");
                        mStartY = y;
                        mLastY = mStartY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float mDeltaY = y - mLastY;

                        float newTansY = translationY + mDeltaY;
                        if (newTansY <= 0 && newTansY >= -toolbar.getHeight()) {
                            toolbar.setTranslationY(newTansY);
                        }
                        mLastY = y;
                        mLastDeltaY = mDeltaY;
//                        Log.v(TAG, "Move");
                        break;
                    case MotionEvent.ACTION_UP:
                        ObjectAnimator animator = null;
                        if (mLastDeltaY < 0 && listView.getFirstVisiblePosition() > 1) {
                            animator = ObjectAnimator.ofFloat(toolbar, "translationY", toolbar.getTranslationY(), -toolbar.getHeight());
                        } else {
                            animator = ObjectAnimator.ofFloat(toolbar, "translationY", toolbar.getTranslationY(), 0);
                        }
                        animator.setDuration(100);
                        animator.start();
                        animator.setInterpolator(AnimationUtils.loadInterpolator(context, android.R.interpolator.linear));
//                        Log.v(TAG, "Up");
                        break;
                }
                return false;
            }
        });
    }

    protected void work() {
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

    //隐藏toolbar
    public void hideToolBar() {
    }


    public boolean requestPermission(String permission, Activity activity, Context context) {
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

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


}
