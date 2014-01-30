package com.ghostbusters.queuer.activites;

import com.ghostbusters.queuer.interfaces.LoginManagerCallback;
import com.ghostbusters.queuer.models.SignInModel;
import com.ghostbusters.queuer.R;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ghostbusters.queuer.models.LoginManager;


public class LoginActivity extends ActionBarActivity implements LoginManagerCallback{
    private SignInModel thisUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ghostbusters.queuer.R.layout.activity_login);


        final ProgressBar progressbar = (ProgressBar)findViewById(com.ghostbusters.queuer.R.id.login_spinner);
        final EditText user = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_username);
        final EditText pass = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_password);
        final Button login = (Button)findViewById(com.ghostbusters.queuer.R.id.btn_login);
        final Button createAccount = (Button)findViewById(com.ghostbusters.queuer.R.id.btn_create_account);

        progressbar.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager manager = new LoginManager();
                manager.setCallback(LoginActivity.this, LoginActivity.this);
                try {
                    manager.login(user.getText().toString(), pass.getText().toString());
                } catch (Exception e) {
                    finishedRequest(false);
                    e.printStackTrace();
                }
            }
        });

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
            Intent i = new Intent(LoginActivity.this, FeedActivity.class);
            startActivity(i);
        } else {
            setMessage("Error Logging In");
        }
    }


}