package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.UserTaskScheduleEntity;

public class UserTaskSchedulesTable extends TableController<UserTaskScheduleEntity> {

    public UserTaskSchedulesTable() {

        super(  "userTaskSchedules",
                new String[]{"userTask", "scheduleTime"});

    }



    // TABLE CREATION

    public void createTable() {

        String query =  "CREATE TABLE userTaskSchedules(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   userTask BIGINT NOT NULL,\n" +
                        "   scheduleTime TIME\n" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE userTaskSchedules\n" +
                        "   ADD FOREIGN KEY (userTask)\n" +
                        "   REFERENCES userTasks(id);";

        executeDbQuery(query);

    }

    public void createExtraConstraints() {}

    public void fillTable() {}



    // PROCESSING OF ENTITY

    public BaseEntity fillEntity(BaseEntity baseEntity) {

        return baseEntity;

    }

}
