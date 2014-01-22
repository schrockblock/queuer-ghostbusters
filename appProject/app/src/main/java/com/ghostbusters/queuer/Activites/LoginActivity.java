package com.ghostbusters.queuer.Activites;

import com.ghostbusters.queuer.Models.LoginManagerCallback;
import com.ghostbusters.queuer.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
// I added this next one
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.demo.queuer.R;
//import com.demo.queuer.managers.LoginManager;
//import com.demo.queuer.managers.LoginManagerCallback;
import com.ghostbusters.queuer.Models.LoginManager;


public class LoginActivity extends ActionBarActivity implements LoginManagerCallback{


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
                    startedRequest();
                    manager.login(user.getText().toString(), pass.getText().toString());
                    //go elsewhere to do finished request
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
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.ghostbusters.queuer.R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case com.ghostbusters.queuer.R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startedRequest() {
        ((ProgressBar)findViewById(R.id.login_spinner)).setVisibility(View.VISIBLE);
        setMessage("Loading");
    }

    //maybe make public so that the loginmanager can update the error message? or maybe add a parameter to finishedRequest.
    public void setMessage(String message){
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

    /**
     * A placeholder fragment containing a simple view.

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(com.ghostbusters.queuer.R.layout.fragment_login, container, false);
            return rootView;
        }
    }
    */
}
