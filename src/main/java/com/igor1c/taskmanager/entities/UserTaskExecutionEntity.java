package com.igor1c.taskmanager.entities;

import com.igor1c.taskmanager.helpers.DateHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserTaskExecutionEntity extends BaseEntity {

    private long userTask;
    private Date executionDate;
    private boolean autoExecution;
    private boolean successfulExecution;



    /* CONSTRUCTORS */

    public UserTaskExecutionEntity() {}

    public UserTaskExecutionEntity(long userTask, Date executionDate, boolean autoExecution, boolean successfulExecution) {

        this.userTask = userTask;
        this.executionDate = executionDate;
        this.autoExecution = autoExecution;
        this.successfulExecution = successfulExecution;

    }

    public UserTaskExecutionEntity(long id, long userTask, Date executionDate, boolean autoExecution, boolean successfulExecution) {

        this(userTask, executionDate, autoExecution, successfulExecution);
        setId(id);

    }



    /* METHODS OF PROCESSING */

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setUserTask(resultSet.getLong(resultSet.findColumn("userTask")));
            setExecutionDate(resultSet.getTimestamp(resultSet.findColumn("executionDate")));
            setAutoExecution(resultSet.getBoolean(resultSet.findColumn("autoExecution")));
            setSuccessfulExecution(resultSet.getBoolean(resultSet.findColumn("successfulExecution")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    /* GETTERS & SETTERS OF THE DATABASE FIELDS */

    public long getUserTask() {
        return userTask;
    }

    public void setUserTask(long userTask) {
        this.userTask = userTask;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public String getExecutionDateString() {
        return DateHelper.dateToString(DateHelper.SDF_YYYYHMMHDD_HHCMMCSS, executionDate);
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public boolean isAutoExecution() {
        return autoExecution;
    }

    public void setAutoExecution(boolean autoExecution) {
        this.autoExecution = autoExecution;
    }

    public boolean isSuccessfulExecution() {
        return successfulExecution;
    }

    public void setSuccessfulExecution(boolean successfulExecution) {
        this.successfulExecution = successfulExecution;
    }

}
