package com.igor1c.database;

import com.igor1c.entities.ActionTypeEntity;

import java.util.HashMap;

public class ActionTypesTable extends TableController<ActionTypeEntity> {

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
            insert(actionTypeEntity);

    }

    public void insert(ActionTypeEntity entity) {

        String query =  "INSERT INTO " + getTableName() + " VALUES (" + entity.getId() + ", '" + entity.getName() + "', '" + entity.getDescription() + "');";
        executeQuery(query);

    }

}
