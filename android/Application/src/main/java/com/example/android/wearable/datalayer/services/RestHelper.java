package com.example.android.wearable.datalayer.services;

/**
 * Created by faky on 12/10/14.
 */


import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class RestHelper {


    private static final String API_URL = "http://192.168.1.179" // 172.27.11.95
                                             +":3000/api/v1";
    private static final String USER_ID = "5438f518411bbf4006bbb606";


    public boolean checkIn() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(API_URL + "/checkIn");

        List<NameValuePair> requestVars = new ArrayList<NameValuePair>(1);
        requestVars.add(new BasicNameValuePair("userId", USER_ID));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(requestVars));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Making HTTP Request
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpResponse response = httpClient.execute(httpPost);
            String responseStr = EntityUtils.toString(response.getEntity());
            return true;
            //Log.d("Http Response:", response.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}