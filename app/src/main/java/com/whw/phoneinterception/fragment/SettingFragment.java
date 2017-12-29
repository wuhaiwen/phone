package com.whw.phoneinterception.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import com.whw.phoneinterception.R;
import com.whw.phoneinterception.activity.WhiteListActivity;

/**
 * Created by wuhaiwen on 2017/12/28 0028.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    SwitchPreference switchPreference;
    PreferenceScreen white_list_settings;
    PreferenceScreen black_list_settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.interception_settings);
        switchPreference = (SwitchPreference) findPreference("switch_reject");
        white_list_settings = (PreferenceScreen) findPreference("white_list");
        switchPreference.setSwitchTextOff("已关闭");
        switchPreference.setSwitchTextOn("已打开");
        switchPreference.setOnPreferenceClickListener(this);
        white_list_settings.setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Intent intent = new Intent();
        switch (preference.getKey()) {
            case "switch_reject":
                switchPreference.setSummary(switchPreference.isChecked() ? "打开" : "关闭");
                break;
            case "white_list":
                intent.setClass(getActivity(),WhiteListActivity.class);
                startActivity(intent);
                break;
            case "black_list":
                break;
            case "reject_mode":
                break;
        }
        return false;
    }

}