package com.ghostbusters.queuer.Models;

/**
 * Created by blakemackall on 1/16/14.
 */
public class SignInModel {

    private static SignInModel signInModel = new SignInModel();

    private SignInModel() {}

    public static SignInModel getInstance(){
        return signInModel;
    }

    private String api_key;
    private String username;
    private String password;
    private String id;

    /*
    public SignInModel(String api_key, String username, String password, String id) {
        this.api_key = api_key;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public SignInModel(String api_key, String username, String password) {
        this.api_key = api_key;
        this.username = username;
        this.password = password;
    }

    public SignInModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SignInModel(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
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
