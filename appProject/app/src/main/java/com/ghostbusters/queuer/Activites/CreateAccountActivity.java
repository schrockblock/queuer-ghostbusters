package com.ghostbusters.queuer.Activites;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.appcompat.R;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

// Not sure if we are going to be using LoginManager and LoginManagerCallback
// I don't think we are, so we will probably change this
import com.demo.queuer.R;
import com.demo.queuer.managers.LoginManager;
import com.demo.queuer.managers.LoginManagerCallback;
import com.ghostbusters.queuer.Models.LoginManager;


/**
 * Created by blakemackall on 1/8/14.
 */
public class CreateAccountActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ghostbusters.queuer.R.layout.activity_create_account);

        Button createAccount = (Button)findViewById(com.ghostbusters.queuer.R.id.btn_creating_account);

        // Add in text fields
        final


        final EditText user = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_create_user);
        final EditText pass = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_create_password);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Again, probably want to make a new manager?
                // Here we will make the request to make a new account
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        // Change this from login to something else?
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Again, this login is probably weird. Put something else there
            View rootView = inflater.inflate(com.ghostbusters.queuer.R.layout.fragment_login, container, false);
            return rootView;
        }
    }

}