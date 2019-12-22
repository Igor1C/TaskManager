package com.igor1c.database;

import com.igor1c.entities.UserTaskEntity;

public class UserTasksTable extends TableController<UserTaskEntity> {

    public UserTasksTable() {
        super("userTasks");
    }

    public void createTable() {

        String query =  "CREATE TABLE userTasks(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL" +
                        ");";

        executeQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

    public void fillTable() {}

    public void insert(UserTaskEntity entity) {}

}
