package com.ghostbusters.queuer.Models;

//to test now, comment out this block of imports and comment out the authenticate method's contents. also see QueuerApplication class
import android.app.DownloadManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ghostbusters.queuer.QueuerApplication;
import org.json.JSONObject;


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
