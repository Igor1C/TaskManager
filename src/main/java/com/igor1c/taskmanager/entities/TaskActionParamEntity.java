package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskActionParamEntity extends BaseEntity {

    private String name;
    private long taskAction;
    private String paramValue;

    private int indexInTaskAction;



    // CONSTRUCTORS

    public TaskActionParamEntity() {}

    public TaskActionParamEntity(String name, long taskAction, String paramValue) {

        setName(name);
        setTaskAction(taskAction);
        setParamValue(paramValue);

    }

    public TaskActionParamEntity(long id, String name, long taskAction, String paramValue) {

        this(name, taskAction, paramValue);
        setId(id);

    }



    // METHODS OF THE PROCESSING

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setName(resultSet.getString(resultSet.findColumn("name")));
            setTaskAction(resultSet.getLong(resultSet.findColumn("taskAction")));
            setParamValue(resultSet.getString(resultSet.findColumn("paramValue")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    // GETTERS & SETTERS OF THE DATABASE FIELDS

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(long taskAction) {
        this.taskAction = taskAction;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }



    // GETTERS & SETTERS OF THE CLASS FIELDS

    public int getIndexInTaskAction() {
        return indexInTaskAction;
    }

    public void setIndexInTaskAction(int indexInTaskAction) {
        this.indexInTaskAction = indexInTaskAction;
    }

}
