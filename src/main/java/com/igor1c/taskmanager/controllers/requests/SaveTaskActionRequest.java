package com.igor1c.taskmanager.controllers.requests;

public class SaveTaskActionRequest extends IdNameRequest {

    long taskOrder;
    long actionType;
    int indexInUserTask;



    public long getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(long taskOrder) {
        this.taskOrder = taskOrder;
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
