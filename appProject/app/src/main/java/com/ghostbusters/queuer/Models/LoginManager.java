package com.ghostbusters.queuer.Models;

//to test now, comment out this block of imports and comment out the authenticate method's contents. also see QueuerApplication class

import android.app.DownloadManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import com.ghostbusters.queuer.Activites.LoginActivity;
import com.ghostbusters.queuer.Constants;
import com.ghostbusters.queuer.QueuerApplication;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

//import com.google.gson.GsonBuilder;


import android.content.Context;



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

    public void createAccount(String username, String password) throws  Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.startedRequest();
        create(username, password);
    }

    private void create(String username, String password) {

        JSONObject createString;

        //maybe have to use a createAccountModel??
        SignInModel model = new SignInModel(username,password);

        try {
            createString = new JSONObject(new Gson().toJson(model));
        } catch (JSONException e) {
            //maybe make this error handling more effective
            createString = null;
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.QUEUER_CREATE_ACCOUNT_URL,
                createString, new Response.Listener<JSONObject>() {
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
                        authenticatedUnsuccessfully();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    authenticatedUnsuccessfully();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((QueuerApplication)context.getApplicationContext()).getRequestQueue().add(request);


    }

    private void authenticate(String username, String password){

        //RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject loginString;
        SignInModel model = new SignInModel(username,password);
        //just a line for a test


        try {
            loginString = new JSONObject(new Gson().toJson(model));
        } catch (JSONException e) {
            //maybe make this error handling more effective
            loginString = null;
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.QUEUER_SESSION_URL,
                loginString, new Response.Listener<JSONObject>() {
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    authenticatedUnsuccessfully();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
