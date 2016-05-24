package sungkyul.ac.kr.leeform.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by Kim on 2016-05-22.
 */
public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_preference_screen_layout);
        addPreferencesFromResource(R.xml.pref_settings);
    }
}