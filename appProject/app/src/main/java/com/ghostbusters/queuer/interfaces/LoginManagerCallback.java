package com.ghostbusters.queuer.interfaces;

/**
 * Created by blakemackall on 1/8/14.
 */
public interface LoginManagerCallback {
    public void startedRequest();
    public void finishedRequest(boolean successful);
}
