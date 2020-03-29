package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ScheduleTypeEntity extends BaseEntity {

    public static final int TYPE_SCHEDULE = 1;
    public static final int TYPE_INTERVAL = 2;

    private static ArrayList<BaseEntity> SCHEDULE_TYPES_ENTITIES_ARRAY;
    private static HashMap<Long, ScheduleTypeEntity> SCHEDULE_TYPE_ENTITIES_MAP;
    private static HashMap<Long, ScheduleTypesEnum> SCHEDULE_TYPES_ENUM_MAP;
    private static HashMap<ScheduleTypesEnum, Long> SCHEDULE_TYPES_ENUM_REVERSE_MAP;

    static {
        initScheduleTypeEntitiesMap();
        initScheduleTypesEnumMaps();
    }

    private String name;
    private String description;



    /* STATIC MAPS */

    public static void initScheduleTypeEntitiesMap() {

        SCHEDULE_TYPE_ENTITIES_MAP = new HashMap<>();

        SCHEDULE_TYPES_ENTITIES_ARRAY = new ArrayList<>();
        SCHEDULE_TYPES_ENTITIES_ARRAY.add(new ScheduleTypeEntity(TYPE_SCHEDULE, "TYPE_SCHEDULE", "Schedule"));
        SCHEDULE_TYPES_ENTITIES_ARRAY.add(new ScheduleTypeEntity(TYPE_INTERVAL, "TYPE_INTERVAL", "Interval"));

        for (BaseEntity scheduleTypeEntity : SCHEDULE_TYPES_ENTITIES_ARRAY) {
            SCHEDULE_TYPE_ENTITIES_MAP.put(scheduleTypeEntity.getId(), (ScheduleTypeEntity) scheduleTypeEntity);
        }

    }

    public static void initScheduleTypesEnumMaps() {

        ArrayList<Integer> scheduleTypesLong = new ArrayList<>(
                Arrays.asList(
                        TYPE_SCHEDULE,
                        TYPE_INTERVAL
                ));

        ArrayList<ScheduleTypesEnum> scheduleTypesEnum = new ArrayList<>(
                Arrays.asList(
                        ScheduleTypesEnum.TYPE_SCHEDULE,
                        ScheduleTypesEnum.TYPE_INTERVAL
                ));

        SCHEDULE_TYPES_ENUM_MAP = new HashMap<>();
        SCHEDULE_TYPES_ENUM_REVERSE_MAP = new HashMap<>();
        for(int i = 0; i < scheduleTypesLong.size(); i++) {
            long curLong = scheduleTypesLong.get(i);
            ScheduleTypesEnum curEnum = scheduleTypesEnum.get(i);

            SCHEDULE_TYPES_ENUM_MAP.put(curLong, curEnum);
            SCHEDULE_TYPES_ENUM_REVERSE_MAP.put(curEnum, curLong);
        }

    }

    public static ArrayList<BaseEntity> getScheduleTypesEntitiesArray() {
        return SCHEDULE_TYPES_ENTITIES_ARRAY;
    }

    public static HashMap<Long, ScheduleTypeEntity> getScheduleTypeEntitiesMap() {
        return SCHEDULE_TYPE_ENTITIES_MAP;
    }

    public static HashMap<Long, ScheduleTypesEnum> getScheduleTypesEnumMap() {
        return SCHEDULE_TYPES_ENUM_MAP;
    }

    public static HashMap<ScheduleTypesEnum, Long> getScheduleTypesEnumReverseMap() {
        return SCHEDULE_TYPES_ENUM_REVERSE_MAP;
    }



    /* CONSTRUCTORS */

    public ScheduleTypeEntity() {}

    public ScheduleTypeEntity(long id, String name, String description) {

        this.id = id;
        this.name = name;
        this.description = description;

    }



    /* GETTERS & SETTERS OF THE CLASS FIELDS */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
