package com.igor1c.database;

import com.igor1c.entities.ActionTypeParamEntity;

import java.util.HashMap;

public class ActionTypeParamsTable extends TableController<ActionTypeParamEntity> {

    public ActionTypeParamsTable() {
        super("actionTypeParams");
    }

    public void createTable() {

        String query =  "CREATE TABLE actionTypeParams(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   description VARCHAR(255) NOT NULL" +
                        ");";

        executeQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

    public void fillTable() {

        HashMap<Integer, ActionTypeParamEntity> predefinedMap = ActionTypeParamEntity.getPredefinedMap();
        for (ActionTypeParamEntity actionTypeParamEntity : predefinedMap.values())
            insert(actionTypeParamEntity);

    }

    public void insert(ActionTypeParamEntity entity) {

        String query =  "INSERT INTO " + getTableName() + " VALUES (" + entity.getId() + ", '" + entity.getName() + "', '" + entity.getDescription() + "');";
        executeQuery(query);

    }

}
