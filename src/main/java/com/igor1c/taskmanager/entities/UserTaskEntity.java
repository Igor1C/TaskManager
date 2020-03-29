package com.igor1c.taskmanager.entities;

import com.igor1c.taskmanager.database.UserTaskSchedulesTable;
import com.igor1c.taskmanager.database.UserTasksTable;
import com.igor1c.taskmanager.helpers.DateHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserTaskEntity extends BaseEntity {

    private String name;

    private ArrayList<BaseEntity> taskActions = new ArrayList<>();
    private ArrayList<BaseEntity> userTaskSchedules = new ArrayList<>();
    private ArrayList<BaseEntity> userTaskExecutions = new ArrayList<>();



    /* CONSTRUCTORS */

    public UserTaskEntity() {}

    public UserTaskEntity(String name) {
        setName(name);
    }

    public UserTaskEntity(long id, String name) {

        setId(id);
        setName(name);

    }



    /* METHODS OF PROCESSING */

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



    /* GETTERS & SETTERS OF THE DATABASE FIELDS */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    /* GETTERS & SETTERS OF THE CLASS FIELDS */

    public ArrayList<BaseEntity> getTaskActions() {
        return taskActions;
    }

    public int getTaskActionsSize() {
        return getTaskActions().size();
    }

    public void setTaskActions(ArrayList<BaseEntity> taskActions) {
        this.taskActions = taskActions;
    }

    public ArrayList<BaseEntity> getUserTaskSchedules() {
        return userTaskSchedules;
    }

    public void setUserTaskSchedules(ArrayList<BaseEntity> userTaskSchedules) {
        this.userTaskSchedules = userTaskSchedules;
    }

    public ArrayList<BaseEntity> getUserTaskExecutions() {
        return userTaskExecutions;
    }

    public void setUserTaskExecutions(ArrayList<BaseEntity> userTaskExecutions) {
        this.userTaskExecutions = userTaskExecutions;
    }

    public UserTaskExecutionEntity getLastExecution() {

        if (userTaskExecutions.size() > 0) {
            return (UserTaskExecutionEntity) userTaskExecutions.get(0);
        } else {
            return null;
        }

    }

    public String getLastExecutionString() {

        UserTaskExecutionEntity userTaskExecutionEntity = getLastExecution();
        if (userTaskExecutionEntity == null) {
            return "";
        } else {
            return userTaskExecutionEntity.getExecutionDateString();
        }

    }

    public String getLastExecutionStringWithoutFillingEntity() {

        UserTasksTable.fillEntityWithUserTaskExecutions(this);
        return getLastExecutionString();

    }



    /* GETTERS WITH NO FIELDS */

    public ArrayList<BaseEntity> getSavedTaskActions() {

        ArrayList<BaseEntity> taskActions = getTaskActions();
        ArrayList<BaseEntity> savedTaskActions = new ArrayList<>();

        for (BaseEntity taskAction : taskActions) {
            if (taskAction.getId() != 0)
                savedTaskActions.add(taskAction);
        }

        return savedTaskActions;

    }

    public UserTaskScheduleEntity getFirstUserTaskSchedule() {

        if (userTaskSchedules.size() == 0) {
            userTaskSchedules.add(new UserTaskScheduleEntity());
        }

        return (UserTaskScheduleEntity) userTaskSchedules.get(0);

    }

    public void setFirstUserTaskSchedule(UserTaskScheduleEntity userTaskScheduleEntity) {

        if (userTaskSchedules.size() > 0) {
            userTaskSchedules.set(0, userTaskScheduleEntity);
        }

    }

}
