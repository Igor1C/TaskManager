package com.igor1c.taskmanager.entities;

import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.helpers.DateHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UserTaskScheduleEntity extends BaseEntity {

    private long userTask;
    private long scheduleType;
    private LocalTime scheduleTime;
    private Long intervalTime;



    /* CONSTRUCTORS */

    public UserTaskScheduleEntity() {}

    public UserTaskScheduleEntity(long userTask, long scheduleType, LocalTime scheduleTime, Long intervalTime) {

        this.userTask = userTask;
        this.scheduleType = scheduleType;
        this.scheduleTime = scheduleTime;
        this.intervalTime = intervalTime;

    }



    /* METHODS OF PROCESSING */

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setScheduleType(resultSet.getLong(resultSet.findColumn("scheduleType")));
            setScheduleTimeFromTime(resultSet.getTime(resultSet.findColumn("scheduleTime")));
            setIntervalTime(resultSet.getLong(resultSet.findColumn("intervalTime")));
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

    public long getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(long scheduleType) {
        this.scheduleType = scheduleType;
    }

    public LocalTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(LocalTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public void setScheduleTimeFromTime(Time time) {

        if (time != null) {
            setScheduleTime(time.toLocalTime());
        } else {
            LocalTime localTime = null;
            setScheduleTime(localTime);
        }

    }

    public String getScheduleTimeString() {

        LocalTime time = getScheduleTime();
        if (time == null) {
            return "";
        } else {
            return "0001-01-01 " + time.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
    }

    public void setScheduleTimeString(String time) {

        if (time != null) {
            setScheduleTimeFromTime(Time.valueOf(time));
        } else {
            LocalTime localTime = null;
            setScheduleTime(localTime);
        }

    }

    public Long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

}
