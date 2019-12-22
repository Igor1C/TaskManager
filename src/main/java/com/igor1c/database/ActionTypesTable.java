package com.igor1c.database;

import com.igor1c.entities.ActionTypeEntity;

import java.util.HashMap;

public class ActionTypesTable extends TableController {

    public ActionTypesTable() {
        super("actionTypes");
    }



    public void createTable() {

        String query =  "CREATE TABLE actionTypes(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   description VARCHAR(255) NOT NULL\n" +
                        ");";

        executeQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

    public void fillTable() {

        HashMap<Integer, ActionTypeEntity> predefinedMap = ActionTypeEntity.getPredefinedMap();
        for (ActionTypeEntity actionTypeEntity : predefinedMap.values())
            insert((int) actionTypeEntity.getId(), actionTypeEntity.getName(), actionTypeEntity.getDescription());

    }

    public void insert(int id, String name, String description) {

        String query =  "INSERT INTO " + getTableName() + " VALUES (" + id + ", '" + name + "', '" + description + "');";
        executeQuery(query);

    }

}
