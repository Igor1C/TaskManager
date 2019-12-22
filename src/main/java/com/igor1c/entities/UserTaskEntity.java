package com.igor1c.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class UserTaskEntity extends BaseEntity {

    private String name;



    public UserTaskEntity() {}

    public UserTaskEntity(long id, String name) {

        setId(id);
        setName(name);

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
