package com.igor1c.entities;

import com.igor1c.entities.BaseEntity;

public class ActionTypeEntity extends BaseEntity {

    private String name;



    public ActionTypeEntity() {}

    public ActionTypeEntity(long id, String name) {

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
