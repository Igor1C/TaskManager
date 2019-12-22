package com.igor1c.database;

public class ActionTypesTable extends TableController {

    public ActionTypesTable() {
        super("actionTypes");
    }


    public void createTable() {

        String query =  "CREATE TABLE actionTypes(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL\n" +
                        ");";

        executeQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

}
