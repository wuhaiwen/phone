package com.whw.phoneinterception.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.whw.phoneinterception.R;

import butterknife.Bind;

public class WhiteListActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_white_list;
    }

    @Override
    protected void work() {
        super.work();
        initToolBar(toolbar,"白名单");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.add_white_list_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()){
            case R.id.action_from_contacts:
                requestPermission(Manifest.permission.READ_CONTACTS, this, getApplicationContext());
                intent.setClass(this,AddWhiteListActivity.class);
                break;
            case R.id.action_define_black_own:
                break;
        }
        startActivity(intent);
        startActivityAnimation();
        return super.onOptionsItemSelected(item);
    }
}
