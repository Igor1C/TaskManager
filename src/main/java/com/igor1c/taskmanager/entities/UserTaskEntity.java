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



    // CONSTRUCTORS

    public UserTaskEntity() {}

    public UserTaskEntity(String name) {
        setName(name);
    }

    public UserTaskEntity(long id, String name) {

        setId(id);
        setName(name);

    }



    // METHODS OF PROCESSING

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setName(resultSet.getString(resultSet.findColumn("name")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void renewTaskActionIndexes() {

        for (int i = 0; i < taskActions.size(); i++) {
            TaskActionEntity taskActionEntity = (TaskActionEntity) taskActions.get(i);
            taskActionEntity.setIndexInUserTask(i);
            taskActionEntity.setTaskOrder(i + 1);
        }

    }



    // GETTERS & SETTERS OF DATABASE FIELDS

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    // GETTERS & SETTERS OF CLASS FIELDS

    public ArrayList<BaseEntity> getTaskActions() {
        return taskActions;
    }

    public int getTaskActionsSize() {
        return getTaskActions().size();
    }

    public void setTaskActions(ArrayList<BaseEntity> taskActions) {
        this.taskActions = taskActions;
    }

    public Date getLastExecution() {
        return lastExecution;
    }

    public String getLastExecutionString() {
        return DateHelper.dateToString(DateHelper.sdf_yyyyhMMhdd_hhcmmcss, getLastExecution());
    }

    public void setLastExecution(Date lastExecution) {
        this.lastExecution = lastExecution;
    }

}
