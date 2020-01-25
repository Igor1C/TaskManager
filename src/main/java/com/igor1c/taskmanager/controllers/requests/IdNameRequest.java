package com.igor1c.taskmanager.controllers.requests;

public class IdNameRequest extends IdRequest {

    String name;



    public IdNameRequest() {}



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
