package com.ghostbusters.queuer.Models;

/**
 * Created by blakemackall on 1/8/14.
 */
public interface LoginManagerCallback {
    public void startedRequest();
    public void finishedRequest(boolean successful);
    public void setMessage(String message);
}
