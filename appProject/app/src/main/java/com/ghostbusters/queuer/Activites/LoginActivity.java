package com.ghostbusters.queuer.Activites;

import com.ghostbusters.queuer.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
//import android.support.v7.appcompat.R;
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


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ghostbusters.queuer.R.layout.activity_login);

        final ProgressBar progressbar = (ProgressBar)findViewById(com.ghostbusters.queuer.R.id.login_spinner);
        final EditText user = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_username);
        final EditText pass = (EditText)findViewById(com.ghostbusters.queuer.R.id.et_password);
        final TextView loading = (TextView)findViewById(R.id.login_spinner_message);
        Button login = (Button)findViewById(com.ghostbusters.queuer.R.id.btn_login);
        Button createAccount = (Button)findViewById(com.ghostbusters.queuer.R.id.btn_create_account);

        progressbar.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager manager = new LoginManager();
                //not sure how to make this functional
                //manager.setCallback(LoginActivity.this, LoginActivity.this);
                try {
                    progressbar.setVisibility(View.VISIBLE);
                    loading.setText("Loading");
                    manager.login(user.getText().toString(), pass.getText().toString());
                    progressbar.setVisibility(View.GONE);
                    loading.setText("");
                } catch (Exception e) {
                    loading.setText("");
                    progressbar.setVisibility(View.GONE);
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
