package com.ghostbusters.queuer;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//to test now, comment these out and comment out the contents of the class. also see loginManager class


/**
 * Created by blakemackall on 1/16/14.
 */
public class QueuerApplication extends Application{


    private RequestQueue queue;
    public RequestQueue getRequestQueue(){
        if (queue == null) queue = Volley.newRequestQueue(this);
        return queue;
    }



}
