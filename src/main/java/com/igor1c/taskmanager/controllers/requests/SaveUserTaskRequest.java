package com.igor1c.taskmanager.controllers.requests;

public class SaveUserTaskRequest {

    int id;
    String idString;
    String name;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {

        try {
            int currentId = Integer.valueOf(idString);
            setId(Integer.valueOf(idString));
        } finally {}

        this.idString = idString;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
