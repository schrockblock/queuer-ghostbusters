package com.example.greekroots;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

/**
 * Created by blakemackall on 1/13/14.
 */
public class FlashcardActivity extends ActionBarActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = (Button)findViewById(R.id.submit);
    }
}
