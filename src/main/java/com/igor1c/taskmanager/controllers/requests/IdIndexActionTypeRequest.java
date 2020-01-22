package com.igor1c.taskmanager.controllers.requests;

public class IdIndexActionTypeRequest extends IdIndexRequest {

    int actionType;



    public IdIndexActionTypeRequest() {}

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

}
