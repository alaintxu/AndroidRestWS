package com.alaintxu.restws;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aperez on 20/05/14.
 */
public class RestCallTask extends AsyncTask<JSONObject,Integer,JSONObject> {
    //possible actions
    public static final int CHECK_KEY   = 1;
    public static final int GET_DATA    = 2;
    public static final int SAVE_DATA   = 3;

    private RestWS rest_ws;

    private int action =   -1;
    private MainActivity myActivity;

    public RestCallTask(){
        super();
        rest_ws = new RestWS();
    }

    public RestCallTask(String ws_url){
        super();
        rest_ws = new RestWS(ws_url);
    }

    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        JSONObject jsonOutput =   null;
        try {
            JSONObject jsonInput    =   jsonObjects[0];
            action = jsonInput.getInt("action");
            jsonOutput = rest_ws.makePostQuery(jsonInput);
        }catch (Exception e) {
            action = -1;
            Log.e("RestCallTask","doInBackground() - "+e.toString());
        }

        return jsonOutput;
    }

    public void setMyActivity(MainActivity myActivity) {
        this.myActivity = myActivity;
    }

    /*protected void onProgressUpdate(Integer... progress){
        setProgressPercent(progress[0]);
    }*/

    protected void onPostExecute(JSONObject jsonOutput){
        switch (action){
            case CHECK_KEY:
                myActivity.setCheckKeyResponse(jsonOutput);
                break;
            case GET_DATA:
                myActivity.setGetDataResponse(jsonOutput);
                break;
            case SAVE_DATA:
                myActivity.setSaveDataResponse(jsonOutput);
                break;
            default:
                Log.e("RestThread","run() - Undefined Action: "+action);
        }
    }
}
