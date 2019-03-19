package com.example.katalogfilm.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;

import com.example.katalogfilm.R;
import com.example.katalogfilm.alarm.AlarmHelper;
import com.example.katalogfilm.data.local.SharedPrefHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingNotifActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getSupportFragmentManager().beginTransaction().add(R.id.content_main, new MainSettings()).commit();
    }

    public static class MainSettings extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

        SharedPrefHelper prefHelper;
        SwitchPreference swDailyNotif, swReleaseNotif;

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(R.xml.settings);
            init();
            setSummaries();
        }

        private void init() {
            swDailyNotif = (SwitchPreference) findPreference(getResources().getString(R.string.notif_harian));
            swReleaseNotif = (SwitchPreference) findPreference(getResources().getString(R.string.notif_rilis));
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getResources().getString(R.string.notif_harian))){
                prefHelper.setDailyNotification(!prefHelper.getDailyNotification());
            }
            if (key.equals(getResources().getString(R.string.notif_rilis))){
                prefHelper.setReleaseNotification(!prefHelper.getReleaseNotification());
            }


        }

        private void setSummaries() {
            prefHelper = new SharedPrefHelper(getContext());
            swDailyNotif.setChecked(prefHelper.getDailyNotification());
            swReleaseNotif.setChecked(prefHelper.getReleaseNotification());
        }
    }
}
