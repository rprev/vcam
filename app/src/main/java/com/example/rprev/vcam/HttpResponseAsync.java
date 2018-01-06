package com.example.rprev.vcam;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.in;

/**
 * Created by ravy on 2018/01/05.
 */

public class HttpResponseAsync extends AsyncTask<String, Integer, String> {

    private Listener listener;
    private String urlSt;
    private HashMap<String,String> headerMap;

    public HttpResponseAsync(String urlSt)
    {
        this.urlSt=urlSt;
        headerMap=new HashMap<String,String>();
    }

    public void addHeader(String k,String v){
        headerMap.put(k,v);
    }

    @Override
    protected String doInBackground(String... args) {

        // デバッグ用
        android.os.Debug.waitForDebugger();
        HttpsURLConnection con=null;
        URL url = null;
        String result = "";

        try {
            url = new URL(urlSt);

            con = (HttpsURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setInstanceFollowRedirects(false);
            con.setDoInput(true);
            //    con.setDoOutput(true);


            for(String key : headerMap.keySet()) {
                con.setRequestProperty(key,headerMap.get(key));
            }

            con.connect();
//            int a = con.getResponseCode();
  //          String s = con.getResponseMessage();


            InputStream in = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String b = null;

            StringBuilder builder = new StringBuilder();
            while ((b = reader.readLine()) != null) {
                builder.append(b);
            }

            reader.close();
            in.close();
            result = builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                con.disconnect();

        }

        return result;
    }

    // 途中経過をメインスレッドに返す
    @Override
    protected void onProgressUpdate(Integer... progress) {
        // ...
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            try {
                listener.onSuccess(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onSuccess(String response) throws JSONException;
    }

}
