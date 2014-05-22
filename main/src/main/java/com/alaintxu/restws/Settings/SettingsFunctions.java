package com.alaintxu.restws.Settings;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by aperez on 22/05/14.
 */
public class SettingsFunctions {
    private static final String FILENAME = "RestWSSettings";

    public static boolean setRestWSSettings(Context context,String server_url,String key) {
       try{
           JSONObject json  =   new JSONObject();
           json.put("server_url",server_url);
           json.put("key",key);
           FileOutputStream fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
           fos.write(json.toString().getBytes());
           return true;
       }catch(Exception e){
           return false;
       }
    }

    public static String getVariable(Context context,String variable){
        try{
            String jsonStr  =   "";

            FileInputStream     fin =   context.openFileInput(FILENAME);
            InputStreamReader   isr =   new InputStreamReader(fin);
            BufferedReader      br  =   new BufferedReader(isr);

            String line =   br.readLine();
            while(line != null){
                jsonStr =   jsonStr+line;
                line    =   br.readLine();
            }
            isr.close();

            JSONObject json =   new JSONObject(jsonStr);
            return json.getString(variable);
        }catch(Exception e){
            return "";
        }
    }
}
