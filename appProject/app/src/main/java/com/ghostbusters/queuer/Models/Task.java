package com.ghostbusters.queuer.models;

import android.content.Context;

import com.ghostbusters.queuer.database.TaskDataSource;

import java.util.Date;

/**
 * Created by blakemackall on 1/17/14.
 */
public class Task {
    private String name;
    private int project_id;
    private int id;
    private int localId;
    private int position;
    private boolean finished;
    private Date created_at;
    private Date updated_at;

    public Task() {}

    public Task(Context context, String name, int project_id, int id, int position, boolean finished, Date created_at, Date updated_at) {
        this.name = name;
        this.project_id = project_id;
        this.id = id;
        this.position = position;
        this.finished = finished;
        this.created_at = created_at;
        this.updated_at = updated_at;

        TaskDataSource dataSource = new TaskDataSource(context);
        dataSource.open();
        setLocalId(dataSource.createTask(name,project_id,id,position,finished).localId);
        dataSource.close();
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
