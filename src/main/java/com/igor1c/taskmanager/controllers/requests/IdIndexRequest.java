package com.igor1c.taskmanager.controllers.requests;

public class IdIndexRequest extends IdRequest {

    int index;



    public IdIndexRequest() {}

    public IdIndexRequest(long id, int index) {

        setId(id);
        setIndex(index);

    }



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
