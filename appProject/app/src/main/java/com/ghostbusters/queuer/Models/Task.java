package com.ghostbusters.queuer.Models;

/**
 * Created by blakemackall on 1/17/14.
 */
public class Task {
    private String name;
    private int project_id;
    private int id;

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
