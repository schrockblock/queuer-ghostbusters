package com.ghostbusters.queuer.models;

/**
 * Created by blakemackall on 1/16/14.
 */
public class SignInModel {

    private String username;
    private String password;

    public SignInModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
