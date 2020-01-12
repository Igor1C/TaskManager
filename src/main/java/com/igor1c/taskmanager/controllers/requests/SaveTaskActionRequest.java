package com.igor1c.taskmanager.controllers.requests;

public class SaveTaskActionRequest {

    long id;
    int order;
    long actionType;
    int indexInUserTask;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
