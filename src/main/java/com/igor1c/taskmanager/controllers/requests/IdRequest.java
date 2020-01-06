package com.igor1c.taskmanager.controllers.requests;

public class IdRequest {

    int id;



    public IdRequest(int id) {
        setId(id);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
