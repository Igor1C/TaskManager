package com.igor1c.taskmanager.controllers.requests;

public class IdRequest {

    long id;



    public IdRequest() {}

    public IdRequest(int id) {
        setId(id);
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
