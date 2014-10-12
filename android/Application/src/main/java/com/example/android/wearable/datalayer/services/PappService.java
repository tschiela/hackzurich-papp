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


public class PappService {


    private static final String API_URL = "http://192.168.1.178" // 172.27.11.95
                                             +":3000/api/v1";
    private static final String USER_ID = "5438f518411bbf4006bbb606";

    public PappService(){
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    protected String fireRequest(String uri, List<NameValuePair> requestVars){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(uri);



        try {
            httpPost.setEntity(new UrlEncodedFormEntity(requestVars));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Making HTTP Request
        try {

            HttpResponse response = httpClient.execute(httpPost);
            String responseStr = EntityUtils.toString(response.getEntity());
            return responseStr.substring(1,responseStr.length()-1);
            //Log.d("Http Response:", response.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkOut(String sessionId){
        List<NameValuePair> requestVars = new ArrayList<NameValuePair>(1);
        requestVars.add(new BasicNameValuePair("id", sessionId));

        String result = fireRequest(API_URL + "/checkOut",requestVars);

        return result != null ? true:false;
    }

    public String checkIn() {
        List<NameValuePair> requestVars = new ArrayList<NameValuePair>(1);
        requestVars.add(new BasicNameValuePair("userId", USER_ID));

        String result = fireRequest(API_URL + "/checkIn",requestVars);
        return result;
    }
}