package com.igor1c.taskmanager.controllers.requests;

import com.igor1c.taskmanager.database.ActionTypesTable;

import javax.validation.constraints.NotBlank;

public class GetActionTypeInfoRequest {

    int id;



    public GetActionTypeInfoRequest(int id) {
        setId(id);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
