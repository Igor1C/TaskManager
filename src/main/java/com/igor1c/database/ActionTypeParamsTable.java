package com.igor1c.database;

public class ActionTypeParamsTable extends TableController {

    public ActionTypeParamsTable() {
        super("actionTypeParams");
    }

    public void createTable() {

        String query =  "CREATE TABLE actionTypeParams(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   actionType BIGINT\n" +
                        ");";

        executeQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE actionTypeParams\n" +
                        "   ADD FOREIGN KEY (actionType)\n" +
                        "   REFERENCES actionTypes(id)";

        executeQuery(query);

    }

    public void createExtraConstraints() {}

}
