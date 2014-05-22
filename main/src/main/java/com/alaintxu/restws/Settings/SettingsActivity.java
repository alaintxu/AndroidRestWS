package com.alaintxu.restws.Settings;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alaintxu.restws.R;

import java.util.List;

public class SettingsActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        setTitle(getResources().getString(R.string.settings));

        setContentView(R.layout.activity_settings);

        Button btn = (Button)findViewById(R.id.save_settings);
        btn.setOnClickListener(this);

        EditText    urlET    =   (EditText)findViewById(R.id.rest_ws_url);
        EditText    keyET    =   (EditText)findViewById(R.id.rest_ws_key);
        urlET.setText(SettingsFunctions.getVariable(getBaseContext(),"server_url"));
        keyET.setText(SettingsFunctions.getVariable(getBaseContext(),"key"));

    }

    @Override
    public void onClick(View v) {
        int viewId  =   v.getId();
        switch (viewId){
            case R.id.save_settings:
                EditText editText   =   (EditText)findViewById(R.id.rest_ws_key);
                String key          =   editText.getText().toString();
                editText            =   (EditText)findViewById(R.id.rest_ws_url);
                String server_url   =   editText.getText().toString();
                SettingsFunctions.setRestWSSettings(getBaseContext(),server_url,key);
                break;
        }
    }
}
