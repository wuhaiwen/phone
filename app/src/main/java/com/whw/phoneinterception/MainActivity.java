package com.whw.phoneinterception;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.whw.phoneinterception.activity.BaseActivity;
import com.whw.phoneinterception.fragment.RecordFragment;
import com.whw.phoneinterception.fragment.RulesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    View view;
    private List<Fragment> fragmentList = new ArrayList<>();
    private long exitTime = 0;

    TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    class TabAdapter extends FragmentStatePagerAdapter {

        String[] title = {"拦截记录", "拦截规则"};

        public TabAdapter(FragmentManager fm) {
            super(fm);
            fragmentList.add(new RecordFragment());
            fragmentList.add(new RulesFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }


    @Override
    public void onBackPressed() {
        //如果不是主界面，则返回主界面
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(0);
            return;
        } else {
            //主界面按返回直接返回到桌面
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
        }
        super.onBackPressed();
    }

}
