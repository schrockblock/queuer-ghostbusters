package com.ghostbusters.queuer.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.ghostbusters.queuer.adapters.FeedAdapter;
import com.ghostbusters.queuer.views.EnhancedListView;
import com.ghostbusters.queuer.models.Project;
import com.ghostbusters.queuer.models.SignInModel;
import com.ghostbusters.queuer.R;

import java.util.ArrayList;
import java.util.Date;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghostbusters.queuer.database.*;


/**
 * Created by blakemackall on 1/15/14.
 */
public class FeedActivity extends ActionBarActivity{
    private FeedAdapter adapter;
    ArrayList<Project> projects;
    ProjectDataSource projectDataSource;
    private String api_key;
    private String id;
    private String username;
    private String password;
    private String getFromSessionURL;
    private SignInModel thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        //local retrieving
        projectDataSource = new ProjectDataSource(this);
        projectDataSource.open();
        projects = projectDataSource.getAllProjects();
        projectDataSource.close();
/*
        //server retrieving
        api_key = SignInModel.getInstance().getApi_key();
        username = SignInModel.getInstance().getUsername();
        password = SignInModel.getInstance().getPassword();
        id = SignInModel.getInstance().getId();

        getFromSessionURL = Constants.QUEUER_CREATE_ACCOUNT_URL + "/" + thisUser.getId() + "/projects";

        JSONObject loginString;

        try {
            loginString = new JSONObject(new Gson().toJson(SignInModel.getInstance()));
        } catch (JSONException e) {
            //maybe make this error handling more effective
            loginString = null;
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getFromSessionURL,
                loginString, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //if we have successful server time:
                //handle response -- are there errors?
                if (!response.has("errors")){

                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((QueuerApplication)FeedActivity.this.getApplicationContext()).getRequestQueue().add(request);

*/



        EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_projects);
        adapter = new FeedAdapter(this, projects);
        listView.setAdapter(adapter);

        listView.setDismissCallback(new EnhancedListView.OnDismissCallback(){
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                final Project project = adapter.getItem(position);
                adapter.remove(position);
                projectDataSource.open();
                projectDataSource.deleteProject(project);
                projectDataSource.close();
                if(adapter.isEmpty()) ((TextView)findViewById(R.id.tv_isEmptyProjectList)).setVisibility(View.VISIBLE);


                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        adapter.insert(project,position);
                        ((TextView) findViewById(R.id.tv_isEmptyProjectList)).setVisibility(View.GONE);
                    }
                };
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(FeedActivity.this, ProjectActivity.class);

                intent.putExtra("project_name", adapter.getItem(position).getTitle());
                intent.putExtra("project_color", adapter.getItem(position).getColor());

                intent.putExtra("project_id", (int)adapter.getItem(position).getLocalId());

                startActivity(intent);

            }
        });



        listView.enableSwipeToDismiss();
        listView.enableRearranging();
        if(adapter.isEmpty()) ((TextView)findViewById(R.id.tv_isEmptyProjectList)).setVisibility(View.VISIBLE);
        else ((TextView)findViewById(R.id.tv_isEmptyProjectList)).setVisibility(View.GONE);


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Intent i = new Intent(FeedActivity.this, LoginActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_add_project) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("New Project");

            View layout = getLayoutInflater().inflate(R.layout.new_project, null);

            final EditText projectTitle = (EditText)layout.findViewById(R.id.projectName);

            final Button bRed = (Button)layout.findViewById(R.id.btn_red);
            final Button bBlue = (Button)layout.findViewById(R.id.btn_blue);
            final Button bYellow = (Button)layout.findViewById(R.id.btn_yellow);
            final Button bGreen = (Button)layout.findViewById(R.id.btn_green);
            final Button bOrange = (Button)layout.findViewById(R.id.btn_orange);
            final Button bPlum = (Button)layout.findViewById(R.id.btn_plum);
            final Button bWhite = (Button)layout.findViewById(R.id.btn_white);

            final int[] projectColor = {getResources().getColor(R.color.White)};

            final View colorSwatch = (View)layout.findViewById(R.id.color_swatch);
            colorSwatch.setBackgroundColor(getResources().getColor(R.color.White));

            bRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = getResources().getColor(R.color.Red);
                    colorSwatch.setBackgroundColor(getResources().getColor(R.color.Red));
                }
            });

            bYellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = getResources().getColor(R.color.Yellow);
                    colorSwatch.setBackgroundColor(getResources().getColor(R.color.Yellow));
                }
            });

            bGreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = getResources().getColor(R.color.Green);
                    colorSwatch.setBackgroundColor(getResources().getColor(R.color.Green));
                }
            });

            bOrange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = getResources().getColor(R.color.Orange);
                    colorSwatch.setBackgroundColor(getResources().getColor(R.color.Orange));
                }
            });

            bBlue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = getResources().getColor(R.color.Blue);
                    colorSwatch.setBackgroundColor(getResources().getColor(R.color.Blue));
                }
            });

            bPlum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = getResources().getColor(R.color.Plum);
                    colorSwatch.setBackgroundColor(getResources().getColor(R.color.Plum));
                }
            });

            bWhite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = getResources().getColor(R.color.White);
                    colorSwatch.setBackgroundColor(getResources().getColor(R.color.White));
                }
            });


            // set dialog message
            alertDialogBuilder
                    //.setMessage(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)))
                    .setCancelable(true)
                    .setView(layout)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    projectDataSource.open();
                                    Project project = projectDataSource.createProject(projectTitle.getText().toString(), projectColor[0], 0, new Date(), new Date());
                                    projectDataSource.close();
                                    adapter.insert(project, 0);
                                    ((TextView) findViewById(R.id.tv_isEmptyProjectList)).setVisibility(View.GONE);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();


            alertDialog.show();
            return true;

        }
        return super.onOptionsItemSelected(item);


    }

}
