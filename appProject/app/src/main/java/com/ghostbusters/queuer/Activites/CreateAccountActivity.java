package com.ghostbusters.queuer.activites;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by blakemackall on 1/8/14.
 *
 */

import com.ghostbusters.queuer.models.LoginManager;
import com.ghostbusters.queuer.interfaces.LoginManagerCallback;
import com.ghostbusters.queuer.models.SignInModel;
import com.ghostbusters.queuer.R;

public class CreateAccountActivity extends ActionBarActivity implements LoginManagerCallback{
    private SignInModel thisUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        final ProgressBar progressbar = (ProgressBar)findViewById(R.id.login_spinner);
        final EditText user = (EditText)findViewById(R.id.et_username);
        final EditText pass = (EditText)findViewById(R.id.et_password);
        final Button createAccount = (Button)findViewById(R.id.btn_creating_account);

        progressbar.setVisibility(View.GONE);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager manager = new LoginManager();
                manager.setCallback(CreateAccountActivity.this, CreateAccountActivity.this);
                try {
                    manager.createAccount(user.getText().toString(), pass.getText().toString());
                } catch (Exception e) {
                    finishedRequest(false);
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void startedRequest() {
        ((ProgressBar)findViewById(R.id.login_spinner)).setVisibility(View.VISIBLE);
        setMessage("Creating Account");
    }

    private void setMessage(String message){
        ((TextView)findViewById(R.id.login_spinner_message)).setText(message);
    }

    @Override
    public void finishedRequest(boolean successful) {
        ((ProgressBar)findViewById(R.id.login_spinner)).setVisibility(View.GONE);
        if (successful){
            setMessage("");
            Intent i = new Intent(CreateAccountActivity.this, FeedActivity.class);
            startActivity(i);

        } else {
            setMessage("Error Creating Account");
        }
    }

}
