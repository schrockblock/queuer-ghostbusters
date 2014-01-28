package com.ghostbusters.queuer.Activites;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;

// Not sure if we are going to be using LoginManager and LoginManagerCallback
// I don't think we are, so we will probably change this

/**
 * Created by blakemackall on 1/8/14.
 *
 */

import com.ghostbusters.queuer.Models.LoginManager;
import com.ghostbusters.queuer.Models.LoginManagerCallback;
import com.ghostbusters.queuer.R;

public class CreateAccountActivity extends ActionBarActivity implements LoginManagerCallback{

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
                // Again, probably want to make a new manager?
                // Here we will make the request to make a new account
                LoginManager manager = new LoginManager();
                manager.setCallback(CreateAccountActivity.this, CreateAccountActivity.this);
                try {
                    manager.createAccount(user.getText().toString(), pass.getText().toString());
                    //say in manager finishedrequest!!!
                } catch (Exception e) {
                    finishedRequest(false);
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        // Change this from login to something else?
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startedRequest() {
        ((ProgressBar)findViewById(R.id.login_spinner)).setVisibility(View.VISIBLE);
        setMessage("Creating Account");
    }

    public void setMessage(String message){
        ((TextView)findViewById(R.id.login_spinner_message)).setText(message);
    }

    @Override
    public void finishedRequest(boolean successful) {
        ((ProgressBar)findViewById(R.id.login_spinner)).setVisibility(View.GONE);
        if (successful){
            setMessage("");
            Intent i = new Intent(CreateAccountActivity.this, FeedActivity.class);
            startActivity(i);
            //maybe other stuff -- LOG THEM IN

        } else {
            setMessage("Error Creating Account");
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
            // Again, this login is probably weird. Put something else there
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            return rootView;
        }
    }
    */


}
