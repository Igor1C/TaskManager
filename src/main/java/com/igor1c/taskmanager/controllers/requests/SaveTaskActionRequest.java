package com.igor1c.taskmanager.controllers.requests;

public class SaveTaskActionRequest {

    int id;
    int actionType;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

}
