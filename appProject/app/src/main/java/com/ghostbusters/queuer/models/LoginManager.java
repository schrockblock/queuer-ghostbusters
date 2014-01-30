package com.ghostbusters.queuer.models;

import android.app.DownloadManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.ghostbusters.queuer.Constants;
import com.ghostbusters.queuer.QueuerApplication;
import org.json.JSONException;
import org.json.JSONObject;

import com.ghostbusters.queuer.interfaces.LoginManagerCallback;
import com.google.gson.Gson;


import android.content.Context;
import android.text.LoginFilter;

import java.util.HashMap;


/**
 * Created by blakemackall on 1/8/14.
 */

public class LoginManager {
    private LoginManagerCallback callback;
    private Context context;
    private static LoginManager login_manager;
    private String requestUrl;

    private LoginManager(){
    }

    public static LoginManager getLogin(){
        if( login_manager == null){
            login_manager = new LoginManager();
        }
        return login_manager;
    }

    public void setCallback(Context context, LoginManagerCallback callback) {
        this.callback = callback;
        this.context = context;
    }

    public void login(String username, String password) throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.startedRequest();
        requestUrl = Constants.QUEUER_SESSION_URL;
        authenticate(username, password);
    }

    public void createAccount(String username, String password) throws  Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.startedRequest();
        requestUrl = Constants.QUEUER_CREATE_ACCOUNT_URL;
        authenticate(username, password);
    }

    private void authenticate(String username, String password){
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        JSONObject loginString;
        SignInModel s_model = new SignInModel(username, password);
        //just a line for a test
        try {
            loginString = new JSONObject(new Gson().toJson(s_model));
        } catch (JSONException e) {
            //maybe make this error handling more effective
            loginString = null;
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, requestUrl,
                loginString, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                //if we have successful server time:
                //handle response -- are there errors?
               if (!response.has("errors")){
                   try {
                       authenticatedSuccessfully();
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               } else {
                   try {
                       //try doing curl requests with different input and see what types of errors there are
                       JSONObject errors = (JSONObject)response.get("errors");
                       authenticatedUnsuccessfully();
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
            }
        }
        ,new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    authenticatedUnsuccessfully();
                    //callback.setMessage("server error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        );
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
