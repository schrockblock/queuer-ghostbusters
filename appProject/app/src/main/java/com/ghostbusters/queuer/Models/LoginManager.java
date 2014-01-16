package com.ghostbusters.queuer.Models;

import android.app.DownloadManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ghostbusters.queuer.QueuerApplication;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by blakemackall on 1/8/14.
 */

public class LoginManager {
    private LoginManagerCallback callback;
    private Context context;

    public void setCallback(Context context, LoginManagerCallback callback) {
        this.callback = callback;
        this.context = context;
    }

    public void login(String username, String password) throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.startedRequest();
        authenticate(username, password);
    }

    private void authenticate(String username, String password){
        RequestQueue queue = Volley.newRequestQueue(context);

        SignInModel model = new SignInModel(username,password);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://queuer-rndapp.rhcloud.com/api/v1/session",
                new JSONObject(new Gson().toJson(model)), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //if we have successful server time:
                //handle response -- are there errors
               if (true){
                   authenticatedSuccessfully();
               } else {
                   authenticatedUnsuccessfully();
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        ((QueuerApplication)context.getApplicationContext()).getRequestQueue().add(request);
    }

    private void authenticatedSuccessfully() throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(true);
    }

    private void authenticatedUnsuccessfully() throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(false);
    }
}
