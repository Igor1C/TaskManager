package com.igor1c.entities;

public abstract class BaseEntity {

    protected long id;
    protected String name;



    public BaseEntity() {}

    public BaseEntity(long id) {
        setId(id);
    }

    public BaseEntity(String name) {
        setName(name);
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
