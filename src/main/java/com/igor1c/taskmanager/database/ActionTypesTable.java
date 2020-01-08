package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.ActionTypeEntity;

import java.util.HashMap;

public class ActionTypesTable extends TableController<ActionTypeEntity> {

    public ActionTypesTable() {

        super(  "actionTypes",
                new String[]{"name", "description"});

    }



    public void createTable() {

        String query =  "CREATE TABLE actionTypes(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   description VARCHAR(255) NOT NULL\n" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

    public void fillTable() {

        HashMap<Long, ActionTypeEntity> predefinedMap = ActionTypeEntity.getPredefinedMap();
        for (ActionTypeEntity actionTypeEntity : predefinedMap.values())
            insert(actionTypeEntity);

    }

}
