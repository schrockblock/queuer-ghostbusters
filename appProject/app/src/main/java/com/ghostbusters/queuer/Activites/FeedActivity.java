package com.ghostbusters.queuer.Activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


import com.ghostbusters.queuer.Adapters.FeedAdapter;
import com.ghostbusters.queuer.EnhancedListView.EnhancedListView;
import com.ghostbusters.queuer.Models.Project;
import com.ghostbusters.queuer.Models.Task;
import com.ghostbusters.queuer.R;

import java.util.ArrayList;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ghostbusters.queuer.database.*;


/**
 * Created by blakemackall on 1/15/14.
 */
public class FeedActivity extends ActionBarActivity{
    private FeedAdapter adapter;
    ArrayList<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);



        final ProjectDataSource projectDataSource = new ProjectDataSource(this);
        projectDataSource.open();
        projects = projectDataSource.getAllProjects();
        //projectDataSource.close();



        EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_projects);
        adapter = new FeedAdapter(this, projects);
        listView.setAdapter(adapter);



        //i dont understand this, ASK Professor!
        listView.setDismissCallback(new EnhancedListView.OnDismissCallback(){
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                final Project project = adapter.getItem(position);
                adapter.remove(position);
                projectDataSource.deleteProject(project);
                if(adapter.isEmpty()) ((TextView)findViewById(R.id.tv_isEmptyProjectList)).setText("No Projects!");
                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        adapter.insert(project,position);
                    }
                };
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FeedActivity.this, ProjectActivity.class);
                intent.putExtra("project_id", (int)adapter.getItemId(position));
                startActivity(intent);
            }
        });



        listView.enableSwipeToDismiss();
        listView.enableRearranging();
        if(adapter.isEmpty()) ((TextView)findViewById(R.id.tv_isEmptyProjectList)).setText("No Projects!");
        else ((TextView)findViewById(R.id.tv_isEmptyProjectList)).setText("");


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //could be the problem
        //final ProjectDataSource projectDataSource = new ProjectDataSource(this);

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

            final Button bRed = (Button)findViewById(R.id.btn_red);
            final Button bBlue = (Button)findViewById(R.id.btn_blue);
            final Button bYellow = (Button)findViewById(R.id.btn_yellow);
            final Button bGreen = (Button)findViewById(R.id.btn_green);
            final Button bOrange = (Button)findViewById(R.id.btn_orange);
            final Button bPlum = (Button)findViewById(R.id.btn_plum);
            final Button bTurq = (Button)findViewById(R.id.btn_turquoise);

            final int[] projectColor = {-1};
/*
            bRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = bRed.getHighlightColor();
                }
            });

            bYellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = bYellow.getHighlightColor();
                    projectTitle.setText("WORKS");
                }
            });

            bGreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = bGreen.getHighlightColor();
                }
            });
*/
            // set dialog message
            alertDialogBuilder
                    //.setMessage(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)))
                    .setCancelable(true)
                    .setView(layout)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Project project = new Project();
                                    project.setTitle(projectTitle.getText().toString());
                                    //project.setColor(projectColor[0]);
                                    //fix this!! not really sure -- is id supposed to be more of a serialization or more of an ordering?
                                    //like should i count deleted projects when i am incrementing the id counter?
                                    //updateIds();
                                    projects.add(0, project);
                                    //projectDataSource.createProject(project.getTitle(),project.getColor(),0,null,null);
                                    ((TextView) findViewById(R.id.tv_isEmptyProjectList)).setText("");
                                    //adapter.insert(project,0);
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
