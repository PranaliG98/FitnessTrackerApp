package com.example.fitnesstrackerapp;


public class Notification {
    private String taskName;
    private String time;
    private String activityType;

    public Notification(String taskName, String time, String activityType) {
        this.taskName = taskName;
        this.time = time;
        this.activityType = activityType;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTime() {
        return time;
    }

    public String getActivityType() {
        return activityType;
    }
}
