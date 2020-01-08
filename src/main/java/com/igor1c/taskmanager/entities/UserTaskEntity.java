package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserTaskEntity extends BaseEntity {

    private String name;
    private ArrayList<BaseEntity> taskActions;



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

    public ArrayList<BaseEntity> getTaskActions() {
        return taskActions;
    }

    public void setTaskActions(ArrayList<BaseEntity> taskActions) {
        this.taskActions = taskActions;
    }

}
