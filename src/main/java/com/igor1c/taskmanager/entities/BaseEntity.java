package com.igor1c.taskmanager.entities;

public abstract class BaseEntity {

    protected long id;



    public BaseEntity() {}



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
