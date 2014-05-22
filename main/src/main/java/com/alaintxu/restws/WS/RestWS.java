package com.alaintxu.restws.WS;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

//import javax.net.ssl.SSLSocketFactory;

/**
 * Created by aperez on 20/05/14.
 */
public class RestWS{
    private String ws_url="https://exampleserver.org/rest/";
    public RestWS(){}
    public RestWS(String ws_url){
        this.ws_url=ws_url;
    }

    public JSONObject makePostQuery(JSONObject json) throws Exception{
        InputStream is = null;
        String error = "";
        JSONObject result = new JSONObject();

        //HttpClient      client  = new DefaultHttpClient();
        HttpClient      client  = getNewHttpClient();
        HttpPost        post    = new HttpPost(ws_url);
        String          jsonstr = json.toString();
        StringEntity    params  = new StringEntity(jsonstr);

        post.addHeader("content-type", "application/x-www-form-urlencoded");
        post.setEntity(params);


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

    /**
     * This function + MySSLSocketFactory is used in order to avoid
     * 'javax.net.ssl.SSLPeerUnverifiedException: No peer certificate' error
     * using not accepted security certificates.
     * @return
     */
    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

}
