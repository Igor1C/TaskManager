package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;

public abstract class BaseEntity {

    protected long id;



    public BaseEntity() {}

    public BaseEntity(ResultSet resultSet) {
        fillFromResultSet(resultSet);
    }



    public void fillFromResultSet(ResultSet resultSet) {}



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
