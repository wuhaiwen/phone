package com.whw.phoneinterception;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.whw.phoneinterception.activity.AddWhiteListActivity;
import com.whw.phoneinterception.activity.BaseActivity;
import com.whw.phoneinterception.activity.SettingsActivity;
import com.whw.phoneinterception.adapter.RecordAdapter;
import com.whw.phoneinterception.fragment.RecordFragment;
import com.whw.phoneinterception.fragment.RulesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recycleView)
    RecyclerView recyclerView;
    RecordAdapter recordAdapter;
    List<String> data = new ArrayList<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void work() {
        super.work();
        ButterKnife.bind(this);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("拦截记录");
//        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    protected void initView() {
        for (int i = 0; i < 20; i++)
            data.add("1");
        recordAdapter = new RecordAdapter(data, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recordAdapter);
    }


    @Override
    public void onBackPressed() {
        //主界面按返回直接返回到桌面
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.settings,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;

        }
        startActivityAnimation();
        return super.onOptionsItemSelected(item);
    }

//    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_settings:
//                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//                    startActivityAnimation();
//                    break;
//            }
//
//            return true;
//        }
//    };
}
