package com.whw.phoneinterception.activity;

import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whw.phoneinterception.R;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    SwitchPreference switchPreference;
    PreferenceScreen white_list_settings;
    PreferenceScreen black_list_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rules);
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
                intent.setClass(this,WhiteListActivity.class);
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
