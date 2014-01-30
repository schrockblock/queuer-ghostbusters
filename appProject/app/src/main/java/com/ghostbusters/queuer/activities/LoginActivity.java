package com.ghostbusters.queuer.activities;

import com.ghostbusters.queuer.interfaces.LoginManagerCallback;
import com.ghostbusters.queuer.models.SignInModel;
import com.ghostbusters.queuer.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.CheckBox;
import android.widget.TextView;
import com.ghostbusters.queuer.models.LoginManager;


public class LoginActivity extends ActionBarActivity implements LoginManagerCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ghostbusters.queuer.R.layout.activity_login);


        final ProgressBar progressbar = (ProgressBar)findViewById(com.ghostbusters.queuer.R.id.login_spinner);
        final EditText user = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_username);
        final EditText pass = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_password);
        final CheckBox remember = (CheckBox)findViewById(R.id.cb_remember);
        final Button login = (Button)findViewById(com.ghostbusters.queuer.R.id.btn_login);
        final Button createAccount = (Button)findViewById(com.ghostbusters.queuer.R.id.btn_create_account);

        progressbar.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remember.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("remember", true);
                    editor.putString("username", user.getText().toString());
                    editor.putString("password", pass.getText().toString());
                    editor.commit();
                }
                LoginManager l_manager = LoginManager.getInstance();
                l_manager.setCallback(LoginActivity.this, LoginActivity.this);
                try {
                    l_manager.login(user.getText().toString(), pass.getText().toString());
                } catch (Exception e) {
                    finishedRequest(false);
                    e.printStackTrace();
                }
            }
        });

        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        if (preferences.getBoolean("remember", false)){
            user.setText(preferences.getString("username", ""));
            pass.setText(preferences.getString("password", ""));
            remember.setChecked(true);
        }

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(i);
            }
        });
    }

    @Override
    public void startedRequest() {
        ((ProgressBar)findViewById(R.id.login_spinner)).setVisibility(View.VISIBLE);
        setMessage("Loading");
    }

    //maybe make public so that the loginmanager can update the error message? or maybe add a parameter to finishedRequest.
    private void setMessage(String message){
        ((TextView)findViewById(R.id.login_spinner_message)).setText(message);
    }

    @Override
    public void finishedRequest(boolean successful) {
        ((ProgressBar)findViewById(R.id.login_spinner)).setVisibility(View.GONE);
        EditText user = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_username);
        EditText pass = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_password);
        if(successful){
            setMessage("");
            startActivity(new Intent(LoginActivity.this, FeedActivity.class));
        } else {
            setMessage("Error Logging In");
        }
    }
}