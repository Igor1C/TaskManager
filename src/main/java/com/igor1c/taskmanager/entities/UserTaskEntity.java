package com.igor1c.taskmanager.entities;

import com.igor1c.taskmanager.helpers.DateHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserTaskEntity extends BaseEntity {

    private String name;

    private ArrayList<BaseEntity> taskActions = new ArrayList<>();
    private Date lastExecution = new Date();



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

    public Date getLastExecution() {
        return lastExecution;
    }

    public String getLastTimeExecutionString() {
        return DateHelper.dateToString(DateHelper.sdf_yyyycMMcdd_hhhmmhsscS, getLastExecution());
    }

    public void setLastExecution(Date lastExecution) {
        this.lastExecution = lastExecution;
    }

}
