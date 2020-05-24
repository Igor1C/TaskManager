package com.igor1c.taskmanager.entities;

import com.igor1c.taskmanager.database.UserTaskSchedulesTable;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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



    /* JSON */

    @Override
    public void insertUpdateFromJsonObject(JSONObject jsonObject) {

        if (jsonObject.has("scheduleType")) {
            setScheduleType(ScheduleTypeEntity
                            .getScheduleTypesEnumReverseMap()
                            .get(ScheduleTypesEnum
                                .getValueFromString(jsonObject.getString("scheduleType"))));
            setScheduleTimeString(jsonObject.getString("scheduleTime"));
            setIntervalTime(jsonObject.getLong("intervalTime"));
        }

    }

    @Override
    public JSONObject toJsonObject() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("scheduleType", getScheduleTypeEnum());
        jsonObject.put("scheduleTime", getScheduleTime());
        jsonObject.put("intervalTime", getIntervalTime());

        return jsonObject;

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

    public ScheduleTypesEnum getScheduleTypeEnum() {

        long currentScheduleType = getScheduleType();
        return ScheduleTypeEntity.getScheduleTypesEnumMap().get(currentScheduleType);

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

        if (time == null) {
            LocalTime localTime = null;
            setScheduleTime(localTime);
        } else if (time.length() == 8) {
            setScheduleTimeFromTime(Time.valueOf(time));
        } else if (time.length() == 5) {
            setScheduleTimeFromTime(Time.valueOf(time + ":00"));
        }

    }

    public Long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

}
