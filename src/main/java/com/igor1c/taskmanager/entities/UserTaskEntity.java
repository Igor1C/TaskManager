package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserTaskEntity extends BaseEntity {

    private String name;
    private ArrayList<BaseEntity> taskActions = new ArrayList<>();



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

    public void renewTaskActionIndexes() {

        for (int i = 0; i < taskActions.size(); i++)
            ((TaskActionEntity) taskActions.get(i)).setIndexInUserTask(i);

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
