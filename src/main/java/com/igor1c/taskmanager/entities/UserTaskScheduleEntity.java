package com.igor1c.taskmanager.entities;

import java.sql.Time;
import java.time.LocalTime;

public class UserTaskScheduleEntity extends BaseEntity {

    private long userTask;
    private LocalTime scheduleTime;



    // CONSTRUCTORS

    public UserTaskScheduleEntity() {}

    public UserTaskScheduleEntity(long userTask, LocalTime scheduleTime) {

        this.userTask = userTask;
        this.scheduleTime = scheduleTime;

    }



    // GETTERS & SETTERS OF THE DATABASE FIELDS

    public long getUserTask() {
        return userTask;
    }

    public void setUserTask(long userTask) {
        this.userTask = userTask;
    }

    public LocalTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(LocalTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

}
