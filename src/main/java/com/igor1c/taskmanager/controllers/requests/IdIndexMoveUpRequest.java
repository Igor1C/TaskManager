package com.igor1c.taskmanager.controllers.requests;

public class IdIndexMoveUpRequest extends IdIndexRequest {

    boolean moveUp;



    public IdIndexMoveUpRequest() {}

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }
}
