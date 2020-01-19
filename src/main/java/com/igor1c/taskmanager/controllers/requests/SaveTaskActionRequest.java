package com.igor1c.taskmanager.controllers.requests;

public class SaveTaskActionRequest {

    long id;
    String name;
    int order;
    long actionType;
    int indexInUserTask;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getActionType() {
        return actionType;
    }

    public void setActionType(long actionType) {
        this.actionType = actionType;
    }

    public int getIndexInUserTask() {
        return indexInUserTask;
    }

    public void setIndexInUserTask(int indexInUserTask) {
        this.indexInUserTask = indexInUserTask;
    }

}
