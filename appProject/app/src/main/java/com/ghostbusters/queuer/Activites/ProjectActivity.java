package com.ghostbusters.queuer.Activites;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostbusters.queuer.R;
import com.ghostbusters.queuer.Adapters.FeedAdapter;
import com.ghostbusters.queuer.Adapters.ProjectAdapter;
import com.ghostbusters.queuer.Models.Task;
import com.ghostbusters.queuer.Models.Project;
import com.ghostbusters.queuer.EnhancedListView.EnhancedListView;
import com.ghostbusters.queuer.database.ProjectDataSource;
import com.ghostbusters.queuer.database.TaskDataSource;
import com.ghostbusters.queuer.database.TaskOpenHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by blakemackall on 1/17/14.
 */
public class ProjectActivity extends ActionBarActivity{
    private int projects_id;
    private ArrayList<Task> tasks;
    private ProjectAdapter adapter;
    TaskDataSource taskDataSource;
    private int project_color;
    private String project_name;
    private Project thisProject;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project);

        projects_id = getIntent().getIntExtra("project_id", -1);

        thisProject = Project.getProject(this,projects_id);
        project_name = thisProject.getTitle();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(project_name);


        project_color = thisProject.getColor();


        taskDataSource = new TaskDataSource(this);
        taskDataSource.open();
        tasks = taskDataSource.getAllTasksForProject(projects_id);
        taskDataSource.close();

        EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_tasks);
        adapter = new ProjectAdapter(this, tasks);
        listView.setAdapter(adapter);

        //listView.setBackgroundColor(12409);

        LinearLayout layout = (LinearLayout)findViewById(R.id.project_screen);
        layout.setBackgroundColor(project_color);

                //also dont understand this, ASK
                listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
                    @Override
                    public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                        final Task task = adapter.getItem(position);
                        adapter.remove(position);
                        taskDataSource.open();
                        taskDataSource.deleteTask(task);
                        taskDataSource.close();
                        if (adapter.isEmpty())
                            ((TextView) findViewById(R.id.tv_isEmptyTaskList)).setVisibility(View.VISIBLE);
                        return new EnhancedListView.Undoable() {
                            @Override
                            public void undo() {
                                adapter.insert(task, position);
                                taskDataSource.open();
                                taskDataSource.createTask(task.getName(), task.getLocalId(), task.getProject_id(), task.getPosition(), task.isFinished());
                                taskDataSource.close();
                                ((TextView) findViewById(R.id.tv_isEmptyTaskList)).setVisibility(View.GONE);
                            }
                        };
                    }
                });

        listView.enableSwipeToDismiss();
        listView.enableRearranging();
        if(adapter.isEmpty()) ((TextView)findViewById(R.id.tv_isEmptyTaskList)).setVisibility(View.VISIBLE);
        else ((TextView)findViewById(R.id.tv_isEmptyTaskList)).setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project, menu);
        getMenuInflater().inflate(R.menu.edit_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //not sure if this button works
        if (id == R.id.action_edit_project){

            final ProjectDataSource projectDataSource = new ProjectDataSource(this);


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("Edit project");

            View layout = getLayoutInflater().inflate(R.layout.new_project, null);

            final EditText projectTitle = (EditText)layout.findViewById(R.id.projectName);

            final Button bRed = (Button)layout.findViewById(R.id.btn_red);
            final Button bBlue = (Button)layout.findViewById(R.id.btn_blue);
            final Button bYellow = (Button)layout.findViewById(R.id.btn_yellow);
            final Button bGreen = (Button)layout.findViewById(R.id.btn_green);
            final Button bOrange = (Button)layout.findViewById(R.id.btn_orange);
            final Button bPlum = (Button)layout.findViewById(R.id.btn_plum);
            final Button bTurq = (Button)layout.findViewById(R.id.btn_turquoise);

            final View colorSwatch = (View)layout.findViewById(R.id.color_swatch);
            colorSwatch.setBackgroundColor(getResources().getColor(R.color.White));

            final int[] projectColor = {project_color};

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

            bTurq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectColor[0] = getResources().getColor(R.color.Turquoise);
                    colorSwatch.setBackgroundColor(getResources().getColor(R.color.Turquoise));
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
                                    thisProject.setTitle(projectTitle.getText().toString());
                                    thisProject.setColor(projectColor[0]);
                                    projectDataSource.open();
                                    projectDataSource.updateProject(thisProject);
                                    projectDataSource.close();
                                    LinearLayout layout = (LinearLayout)findViewById(R.id.project_screen);
                                    layout.setBackgroundColor(thisProject.getColor());
                                    ActionBar actionBar = getSupportActionBar();
                                    actionBar.setTitle(thisProject.getTitle());
                                    //adapter.insert(project, 0);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            //alertDialogBuilder.set
            AlertDialog alertDialog = alertDialogBuilder.create();


            alertDialog.show();
            return true;

        }
        if (id == R.id.action_add_task) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("New Task");

            View layout = getLayoutInflater().inflate(R.layout.new_task, null);

            final EditText taskTitle = (EditText)layout.findViewById(R.id.task);

            // set dialog message
            alertDialogBuilder
                    //.setMessage(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)))
                    .setCancelable(true)
                    .setView(layout)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    taskDataSource.open();
                                    Task task = taskDataSource.createTask(taskTitle.getText().toString(),projects_id,0,0,false);
                                    taskDataSource.close();
                                    adapter.insert(task,0);
                                    adapter.notifyDataSetChanged();
                                    ((TextView)findViewById(R.id.tv_isEmptyTaskList)).setVisibility(View.GONE);
                                }
                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {}
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
