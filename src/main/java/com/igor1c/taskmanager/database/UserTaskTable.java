package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.UserTaskEntity;

public class UserTaskTable extends TableController<UserTaskEntity> {

    public UserTaskTable() {

        super(  "userTasks",
                new String[]{"name"});

    }

    public void createTable() {

        String query =  "CREATE TABLE userTasks(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

    public void fillTable() {}

}
