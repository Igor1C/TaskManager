package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTaskEntity extends BaseEntity {

    private String name;



    public UserTaskEntity() {}

    public UserTaskEntity(String name) {
        setName(name);
    }

    public UserTaskEntity(long id, String name) {

        setId(id);
        setName(name);

    }



    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setName(resultSet.getString(resultSet.findColumn("name")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
