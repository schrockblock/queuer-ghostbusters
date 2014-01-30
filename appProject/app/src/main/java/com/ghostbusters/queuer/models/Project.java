package com.ghostbusters.queuer.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import com.ghostbusters.queuer.database.ProjectDataSource;

/**
 * Created by blakemackall on 1/15/14.
 */
public class Project {
    private int id;
    private String name;
    private int color;
    private int localId;

    public Project() {}

    public Project(Context context, int id, String name) {
        this.id = id;
        this.name = name;

        ProjectDataSource projectDataSource = new ProjectDataSource(context);
        projectDataSource.open();
        localId = projectDataSource.createProject(name,0,id,new Date(),new Date()).localId;
        projectDataSource.close();
    }

    public int getLocalId() { return localId; }

    public void setLocalId(int localId) { this.localId = localId; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    static public Project getProject(Context context,int LocalId){
        ProjectDataSource projectDataSource = new ProjectDataSource(context);
        projectDataSource.open();
        Project newProject = projectDataSource.getProject(LocalId);
        projectDataSource.close();
        return newProject;
    }
}
