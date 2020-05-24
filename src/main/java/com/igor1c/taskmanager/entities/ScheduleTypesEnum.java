package com.igor1c.taskmanager.entities;

public enum ScheduleTypesEnum {

    TYPE_SCHEDULE,
    TYPE_INTERVAL;

    public static ScheduleTypesEnum getValueFromString(String valueString) {

        if (valueString.equals("TYPE_SCHEDULE")) {
            return TYPE_SCHEDULE;
        } else if (valueString.equals("TYPE_INTERVAL")) {
            return TYPE_INTERVAL;
        }

        return null;

    }

}
