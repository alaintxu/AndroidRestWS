package com.alaintxu.restws;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

    }
    @Override
    public void onClick(View v){
        saveKey(v);
    }
    public void saveKey(View v) {
        Toast.makeText(this.getActivity(), "SaveKey", Toast.LENGTH_SHORT).show();
    }
}
