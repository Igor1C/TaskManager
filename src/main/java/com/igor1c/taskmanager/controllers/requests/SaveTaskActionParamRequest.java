package com.igor1c.taskmanager.controllers.requests;

public class SaveTaskActionParamRequest extends IdNameRequest {

    long taskAction;
    String paramValue;
    int indexInTaskAction;
    int taskActionIndexInUserTask;



    public long getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(long taskAction) {
        this.taskAction = taskAction;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public int getIndexInTaskAction() {
        return indexInTaskAction;
    }

    public void setIndexInTaskAction(int indexInTaskAction) {
        this.indexInTaskAction = indexInTaskAction;
    }

    public int getTaskActionIndexInUserTask() {
        return taskActionIndexInUserTask;
    }

    public void setTaskActionIndexInUserTask(int taskActionIndexInUserTask) {
        this.taskActionIndexInUserTask = taskActionIndexInUserTask;
    }

}
