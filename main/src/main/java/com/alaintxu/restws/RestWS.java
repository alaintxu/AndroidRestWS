package com.alaintxu.restws;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Created by aperez on 20/05/14.
 */
public class RestWS{
    private String ws_url="http://alaintxu.no-ip.org/rest";
    public RestWS(){}
    public RestWS(String ws_url){
        this.ws_url=ws_url;
    }

    public JSONObject makePostQuery(JSONObject json) throws Exception{
        InputStream is = null;
        String error = "";
        JSONObject result = new JSONObject();

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(ws_url);
        post.setEntity(new StringEntity(json.toString()));
        HttpResponse http_response = client.execute(post);
        is = http_response.getEntity().getContent();
        if(is!=null){
            String json_str = convertInputStreamToString(is);
            result = new JSONObject(json_str);
        }else{
            error = "Empty response";
        }

        if(!error.equals("")){
            result.put("error",error);
            Log.e("RestWS", "checkKey() - " + error);
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
