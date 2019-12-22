package com.igor1c.database;

public class TaskActionsTable extends TableController {

    public TaskActionsTable() {
        super("taskActions");
    }

    public void createTable() {

        String query =  "CREATE TABLE taskActions(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   actionType BIGINT NOT NULL,\n" +
                        "   userTask BIGINT NOT NULL\n" +
                        ");";

        executeQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE taskActions\n" +
                        "   ADD FOREIGN KEY (actionType)\n" +
                        "   REFERENCES actionTypes(id);\n" +
                        "ALTER TABLE taskActions\n" +
                        "   ADD FOREIGN KEY (userTask)\n" +
                        "   REFERENCES userTasks(id);";

        executeQuery(query);

    }

    public void createExtraConstraints() {}

}
