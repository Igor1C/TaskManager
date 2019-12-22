package com.igor1c.entities;

import com.igor1c.entities.BaseEntity;

public class ActionTypeEntity extends BaseEntity {

    private String description;



    public ActionTypeEntity() {}

    public ActionTypeEntity(long id, String name, String description) {

        setId(id);
        setName(name);

    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
