package com.xinluhuang.englishlearn.module.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xinluhuang.englishlearn.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        GeneralPreferenceFragment generalPreferenceFragment=GeneralPreferenceFragment.newInstance("","");
        getFragmentManager().beginTransaction().replace(R.id.container,generalPreferenceFragment).commit();
    }
}
